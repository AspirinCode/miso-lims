/*
 * Copyright (c) 2012. The Genome Analysis Centre, Norwich, UK
 * MISO project contacts: Robert Davey @ TGAC
 * *********************************************************************
 *
 * This file is part of MISO.
 *
 * MISO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MISO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MISO.  If not, see <http://www.gnu.org/licenses/>.
 *
 * *********************************************************************
 */

package uk.ac.bbsrc.tgac.miso.core.data.impl;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.eaglegenomics.simlims.core.User;

import uk.ac.bbsrc.tgac.miso.core.data.AbstractSample;
import uk.ac.bbsrc.tgac.miso.core.data.ChangeLog;
import uk.ac.bbsrc.tgac.miso.core.data.QcCorrespondingField;
import uk.ac.bbsrc.tgac.miso.core.data.impl.changelog.SampleChangeLog;

/**
 * uk.ac.bbsrc.tgac.miso.core.data.impl
 * <p/>
 * TODO Info
 * 
 * @author Rob Davey
 * @since 0.0.2
 */
@Entity
@Table(name = "Sample")
@Inheritance(strategy = InheritanceType.JOINED)
public class SampleImpl extends AbstractSample {

  private static final long serialVersionUID = 1L;

  /**
   * Construct a new Sample with a default empty SecurityProfile
   */
  public SampleImpl() {
  }

  @Override
  public ChangeLog createChangeLog(String summary, String columnsChanged, User user) {
    SampleChangeLog changeLog = new SampleChangeLog();
    changeLog.setSample(this);
    changeLog.setSummary(summary);
    changeLog.setColumnsChanged(columnsChanged);
    changeLog.setUser(user);
    return changeLog;
  }

  public void updateFromQc(QcCorrespondingField correspondingField, double value, String units) {
    correspondingField.updateField(this, value, units);
  }

}
