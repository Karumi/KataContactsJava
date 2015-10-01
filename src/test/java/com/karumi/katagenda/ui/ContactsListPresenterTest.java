/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karumi.katagenda.ui;

import com.karumi.katagenda.domain.Contact;
import com.karumi.katagenda.usecase.AddContact;
import com.karumi.katagenda.usecase.GetContacts;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactsListPresenterTest {

  private static final int ANY_NUMBER_OF_CONTACTS = 7;
  private static final String ANY_FIRST_NAME = "Pedro Vicente";
  private static final String ANY_LAST_NAME = "Gomez Sanchez";
  private static final String ANY_PHONE_NUMBER = "666666666";

  @Mock private ContactsListPresenter.View view;
  @Mock private GetContacts getContacts;
  @Mock private AddContact addContact;
  @Captor private ArgumentCaptor<List<Contact>> contactsCaptor;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void shouldShowWelcomeMessageOnInitialize() {
    ContactsListPresenter presenter = givenAContactsListPresenter();

    presenter.onInitialize();

    verify(view).showWelcomeMessage();
  }

  @Test public void shouldShowEmptyCaseIfTheAgendaIsEmpty() {
    ContactsListPresenter presenter = givenAContactsListPresenter();
    givenTheAgendaIsEmpty();

    presenter.onInitialize();

    verify(view).showEmptyCase();
  }

  @Test public void shouldShowContactsFromTheAgendaOnInitialize() {
    ContactsListPresenter presenter = givenAContactsListPresenter();
    List<Contact> contacts = givenTheAgendaIsNotEmpty();

    presenter.onInitialize();

    verify(view).showContacts(contacts);
  }

  @Test public void shouldShowGoodbyeMessageOnStop() {
    ContactsListPresenter presenter = givenAContactsListPresenter();

    presenter.onStop();

    verify(view).showGoodbyeMessage();
  }

  @Test public void shouldShowTheContactsListWithTheNewContactOnContactAdded() {
    ContactsListPresenter presenter = givenAContactsListPresenter();
    Contact contactToCreate = givenTheUserAddsAContact();
    givenTheContactIsAddedCorrectly(contactToCreate);

    presenter.onInitialize();
    presenter.onAddContactOptionSelected();

    verify(view, times(2)).showContacts(contactsCaptor.capture());
    List<Contact> newContacts = contactsCaptor.getAllValues().get(1);
    assertTrue(newContacts.contains(contactToCreate));
  }

  @Test public void shouldShowAnErrorIfTheFirstNameOfTheNewContactIsEmpty() {
    ContactsListPresenter presenter = givenAContactsListPresenter();
    givenTheUserTypesContactInfo("", ANY_LAST_NAME, ANY_PHONE_NUMBER);

    presenter.onInitialize();
    presenter.onAddContactOptionSelected();

    verify(view).showDefaultError();
  }

  @Test public void shouldShowAnErrorIfTheLastNameOfTheNewContactIsEmpty() {
    ContactsListPresenter presenter = givenAContactsListPresenter();
    givenTheUserTypesContactInfo(ANY_FIRST_NAME, "", ANY_PHONE_NUMBER);

    presenter.onInitialize();
    presenter.onAddContactOptionSelected();

    verify(view).showDefaultError();
  }

  @Test public void shouldShowAnErrorIfTheNameOfTheNewContactIsEmpty() {
    ContactsListPresenter presenter = givenAContactsListPresenter();
    givenTheUserTypesContactInfo(ANY_FIRST_NAME, ANY_LAST_NAME, "");

    presenter.onInitialize();
    presenter.onAddContactOptionSelected();

    verify(view).showDefaultError();
  }

  private void givenTheUserTypesContactInfo(String t, String anyLastName, String anyPhoneNumber) {
    when(view.getNewContactFirstName()).thenReturn(t);
    when(view.getNewContactLastName()).thenReturn(anyLastName);
    when(view.getNewContactPhoneNumber()).thenReturn(anyPhoneNumber);
  }

  private void givenTheContactIsAddedCorrectly(Contact contact) {
    when(addContact.execute(contact)).thenReturn(contact);
    LinkedList<Contact> newContacts = new LinkedList<>();
    newContacts.add(contact);
    when(getContacts.execute()).thenReturn(newContacts);
  }

  private void givenTheAgendaIsEmpty() {
    when(getContacts.execute()).thenReturn(Collections.<Contact>emptyList());
  }

  private List<Contact> givenTheAgendaIsNotEmpty() {
    List<Contact> contacts = new LinkedList<>();
    for (int i = 0; i < ANY_NUMBER_OF_CONTACTS; i++) {
      Contact contact = new Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER);
      contacts.add(contact);
    }
    when(getContacts.execute()).thenReturn(contacts);
    return contacts;
  }

  private ContactsListPresenter givenAContactsListPresenter() {
    return new ContactsListPresenter(view, getContacts, addContact);
  }

  private Contact givenTheUserAddsAContact() {
    Contact contact = new Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER);
    givenTheUserTypesContactInfo(contact.getFirstName(), contact.getLastName(),
        contact.getPhoneNumber());
    return contact;
  }
}