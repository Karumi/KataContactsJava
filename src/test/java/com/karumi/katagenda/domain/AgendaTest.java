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

package com.karumi.katagenda.domain;

import com.karumi.katagenda.common.repository.DataSource;
import com.karumi.katagenda.common.repository.InMemoryDataSource;
import com.karumi.katagenda.common.repository.Repository;
import com.karumi.katagenda.domain.repository.ContactsRepository;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AgendaTest {

  private static final String ANY_FIRST_NAME = "Pedro Vicente";
  private static final String ANY_LAST_NAME = "Gomez Sanchez";
  private static final String ANY_PHONE_NUMBER = "666666666";

  @Test public void shouldReturnAnEmptyListOfContactsIfTheAgendaIsEmpty() {
    Agenda agenda = givenAnAgenda();

    List<Contact> contacts = agenda.getContacts();

    assertTrue(contacts.isEmpty());
  }

  @Test public void shouldReturnTheContactCreatedOnContactAdded() {
    Agenda agenda = givenAnAgenda();
    Contact contactToAdd = givenAnyContact();

    Contact createdContact = agenda.addContact(contactToAdd);

    assertEquals(contactToAdd, createdContact);
  }

  @Test public void shouldReturnTheNewContactAfterTheCreationUsingGetContacts() {
    Agenda agenda = givenAnAgenda();
    Contact contact = givenAnyContact();

    agenda.addContact(contact);

    List<Contact> contacts = agenda.getContacts();
    assertTrue(contacts.contains(contact));
    assertEquals(1, contacts.size());
  }

  private Contact givenAnyContact() {
    return new Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER);
  }

  private Agenda givenAnAgenda() {
    DataSource<Contact> dataSource = new InMemoryDataSource<>();
    Repository<Contact> contactsRepository = new ContactsRepository(dataSource);
    return new Agenda(contactsRepository);
  }
}
