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

ListTarget.group = {
  name: "Groups",
  createUrl: function(config, projectId) {
    throw new Error("Static data only");
  },
  queryUrl: null,
  createBulkActions: function(config, projectId) {
    return [];
  },
  createStaticActions: function(config, projectId) {
    if (config.isAdmin && !config.userId) {
      return [{
        "name": "Add",
        "handler": function() {
          window.location = "/miso/admin/group/new";
        }
      }];
    } else {
      return [];
    }
  },
  createColumns: function(config, projectId) {
    return [{
      "sTitle": "Group Name",
      "mData": "name",
      "include": true,
      "iSortPriority": 1,
      "bSortDirection": true,
      "mRender": function(data, type, full) {
        if (config.isAdmin) {
          return "<a href=\"/miso/admin/group/" + full.id + "\">" + data + "</a>";
        } else {
          return data;
        }
      }
    }, {
      "sTitle": "Description",
      "mData": "description",
      "include": true,
      "iSortPriority": 0
    }];
  }
};
