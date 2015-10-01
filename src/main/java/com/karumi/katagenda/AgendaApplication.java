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

package com.karumi.katagenda;

import com.karumi.katagenda.common.ui.Presenter;
import com.karumi.katagenda.servicelocator.AgendaServiceLocator;
import com.karumi.katagenda.ui.ContactsListPresenter;

public class AgendaApplication {

  public static void main(String[] args) {
    ContactsListPresenter presenter = getPresenter();
    presenter.onInitialize();
    while (true) {
      presenter.onAddContactOptionSelected();
    }
  }

  private static ContactsListPresenter getPresenter() {
    ContactsListPresenter contactsListPresenter = AgendaServiceLocator.getContactsListPresenter();
    hookPresenterStopEvent(contactsListPresenter);
    return contactsListPresenter;
  }

  private static void hookPresenterStopEvent(final Presenter presenter) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override public void run() {
        presenter.onStop();
      }
    });
  }
}
