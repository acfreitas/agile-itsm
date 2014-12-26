/*
 *     Copyright 2014 Modo Ágil
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package br.com.agileitsm.model.support;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract entity for model entity<br />
 *
 * All model entities from model must extend this class or a sub-class
 *
 * @since 24/12/2014
 * @author Bruno César Ribeiro e Silva - <a href="mailto:bruno@brunocesar.com">bruno@brunocesar.com</a>
 */
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = -4073371653093846183L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long version;

    @Getter
    @Setter
    private Boolean removed;

    @Getter
    @Setter
    private Date changeDate;

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        return String.format("%s id: %s", this.getClass().getSimpleName(), getId());
    }

}
