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

import com.karumi.katagenda.common.ui.Presenter;
import com.karumi.katagenda.domain.Contact;
import com.karumi.katagenda.usecase.AddContact;
import com.karumi.katagenda.usecase.GetContacts;
import java.util.List;

public class ContactsListPresenter extends Presenter<ContactsListPresenter.View> {

  private final GetContacts getContacts;
  private final AddContact addContact;

  public ContactsListPresenter(ContactsListPresenter.View view, GetContacts getContacts,
      AddContact addContact) {
    super(view);
    this.getContacts = getContacts;
    this.addContact = addContact;
  }

  @Override public void onInitialize() {
    getView().showWelcomeMessage();
    loadContactsList();
  }

  @Override public void onStop() {
    getView().showGoodbyeMessage();
  }

  public void onAddContactOptionSelected() {
    Contact contactToAdd = requestNewContact();
    if (contactToAdd == null) {
      getView().showDefaultError();
    } else {
      addContact.execute(contactToAdd);
      loadContactsList();
    }
  }

  private Contact requestNewContact() {
    View view = getView();
    String firstName = view.getNewContactFirstName();
    String lastName = view.getNewContactLastName();
    String phoneNumber = view.getNewContactPhoneNumber();
    Contact contact = null;
    if (isContactInfoValue(firstName, lastName, phoneNumber)) {
      contact = new Contact(firstName, lastName, phoneNumber);
    }
    return contact;
  }

  private boolean isContactInfoValue(String firstName, String lastName, String phoneNumber) {
    return !firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty();
  }

  private void loadContactsList() {
    List<Contact> contactList = getContacts.execute();
    if (contactList.isEmpty()) {
      getView().showEmptyCase();
    } else {
      getView().showContacts(contactList);
    }
  }

  public interface View extends Presenter.View {

    void showWelcomeMessage();

    void showGoodbyeMessage();

    void showContacts(List<Contact> contactList);

    String getNewContactFirstName();

    String getNewContactLastName();

    String getNewContactPhoneNumber();
  }
}
