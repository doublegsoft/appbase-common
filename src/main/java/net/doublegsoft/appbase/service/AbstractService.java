/*
 * Copyright 2016 doublegsoft.net
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
 */

package net.doublegsoft.appbase.service;

import net.doublegsoft.appbase.dao.CommonDataAccess;
import net.doublegsoft.appbase.dao.DataAccessException;
import net.doublegsoft.appbase.dao.JdbcCommonDataAccess;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class AbstractService {

    protected CommonDataAccess commonDataAccess;

    private static boolean txAuto = true;

    /**
     * @param commonDataAccess
     *            the commonDataAccess to set
     */
    public void setCommonDataAccess(CommonDataAccess commonDataAccess) {
        this.commonDataAccess = commonDataAccess;
    }

    public void begin() {
        if (commonDataAccess instanceof JdbcCommonDataAccess) {
            try {
                ((JdbcCommonDataAccess) commonDataAccess).beginTransaction();
            } catch (DataAccessException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public void begin(int level) {
        if (commonDataAccess instanceof JdbcCommonDataAccess) {
            try {
                ((JdbcCommonDataAccess) commonDataAccess).beginTransaction(level);
            } catch (DataAccessException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public void rollback() {
        if (commonDataAccess instanceof JdbcCommonDataAccess) {
            try {
                ((JdbcCommonDataAccess) commonDataAccess).rollback();
            } catch (DataAccessException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public void commit() {
        if (commonDataAccess instanceof JdbcCommonDataAccess) {
            try {
                ((JdbcCommonDataAccess) commonDataAccess).commit();
            } catch (DataAccessException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public void setAutoTransactionManagement(boolean flag) {
        txAuto = flag;
    }

    public boolean isAutoTransactionManagement() {
        return txAuto;
    }

}
