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

package com.karumi.katagenda.servicelocator;

import com.karumi.katagenda.common.repository.DataSource;
import com.karumi.katagenda.common.repository.InMemoryDataSource;
import com.karumi.katagenda.common.repository.Repository;
import com.karumi.katagenda.domain.Agenda;
import com.karumi.katagenda.domain.Contact;
import com.karumi.katagenda.domain.repository.ContactsRepository;
import com.karumi.katagenda.ui.ContactsListPresenter;
import com.karumi.katagenda.ui.SysOutContactsListView;
import com.karumi.katagenda.usecase.AddContact;
import com.karumi.katagenda.usecase.GetContacts;

public class AgendaServiceLocator {

  private final static DataSource<Contact> IN_MEMORY_CONTACTS_DATA_SOURCE =
      new InMemoryDataSource<>();

  public static ContactsListPresenter getContactsListPresenter() {
    SysOutContactsListView sysOutView = getSysOutView();
    GetContacts getContacts = getContacts();
    AddContact addContact = getAddContact();
    return new ContactsListPresenter(sysOutView, getContacts, addContact);
  }

  private static SysOutContactsListView getSysOutView() {
    return new SysOutContactsListView();
  }

  private static GetContacts getContacts() {
    Agenda agenda = getAgenda();
    return new GetContacts(agenda);
  }

  private static AddContact getAddContact() {
    Agenda agenda = getAgenda();
    return new AddContact(agenda);
  }

  private static Agenda getAgenda() {
    Repository<Contact> contactsRepository = getContactsRepository();
    return new Agenda(contactsRepository);
  }

  private static Repository<Contact> getContactsRepository() {
    return new ContactsRepository(IN_MEMORY_CONTACTS_DATA_SOURCE);
  }
}
