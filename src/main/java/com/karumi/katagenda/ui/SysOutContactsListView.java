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
import java.util.List;
import java.util.Scanner;

public class SysOutContactsListView implements ContactsListPresenter.View {

  @Override public void showWelcomeMessage() {
    print("Welcome to your awesome agenda!");
    print("I'm going to ask you about some of your contacts information :)");
  }

  @Override public void showGoodbyeMessage() {
    print("\nSee you soon!");
  }

  @Override public void showContacts(List<Contact> contactList) {
    for (Contact contact : contactList) {
      String firstName = contact.getFirstName();
      String lastName = contact.getLastName();
      String phoneNumber = contact.getPhoneNumber();
      print(firstName + " - " + lastName + " - " + phoneNumber);
    }
  }

  @Override public String getNewContactFirstName() {
    print("First name:");
    return readLine();
  }

  @Override public String getNewContactLastName() {
    print("Last name:");
    return readLine();
  }

  @Override public String getNewContactPhoneNumber() {
    print("Phone number:");
    return readLine();
  }

  @Override public void showDefaultError() {
    print("Ups, something went wrong :( Try again!");
  }

  @Override public void showEmptyCase() {
    print("Your agenda is empty!");
  }

  private void print(String line) {
    System.out.println(line);
  }

  private String readLine() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
}
