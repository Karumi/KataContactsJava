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
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void shouldShowContactsFromTheAgendaOnInitialize() {
    ContactsListPresenter presenter = givenAContactsListPresenter();
    List<Contact> contacts = givenTheAgendaIsNotEmpty();

    presenter.onInitialize();

    verify(view).showContacts(contacts);
  }

  private List<Contact> givenTheAgendaIsNotEmpty() {
    List<Contact> contacts = new LinkedList<>();
    for (int i = 0; i < ANY_NUMBER_OF_CONTACTS; i++) {
      Contact contact = new Contact(ANY_FIRST_NAME,ANY_LAST_NAME,ANY_PHONE_NUMBER);
      contacts.add(contact);
    }
    when(getContacts.execute()).thenReturn(contacts);
    return contacts;
  }

  private ContactsListPresenter givenAContactsListPresenter() {
    return new ContactsListPresenter(view, getContacts, addContact);
  }
}