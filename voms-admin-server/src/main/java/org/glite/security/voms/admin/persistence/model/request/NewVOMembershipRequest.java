/**
 * Copyright (c) Members of the EGEE Collaboration. 2006-2009.
 * See http://www.eu-egee.org/partners/ for details on the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors:
 * 	Andrea Ceccanti (INFN)
 */
package org.glite.security.voms.admin.persistence.model.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "vo_membership_req")
public class NewVOMembershipRequest extends Request {

  /**
     * 
     */
  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  String confirmId;

  public NewVOMembershipRequest() {

    // TODO Auto-generated constructor stub
  }

  /**
   * @return the confirmId
   */
  public String getConfirmId() {

    return confirmId;
  }

  /**
   * @param confirmId
   *          the confirmId to set
   */
  public void setConfirmId(String confirmId) {

    this.confirmId = confirmId;
  }

  public String getTypeName() {

    return "VO membership request";
  }

  @Override
  public String toString() {

    ToStringBuilder builder = new ToStringBuilder(this);

    builder.appendSuper(super.toString()).append("confirmId", confirmId);
    return builder.toString();
  }

}
