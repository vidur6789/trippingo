/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package trippingo.optaplanner.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.AbstractPersistable;

public class TimeGrain {

    /**
     * Time granularity is 15 minutes (which is often recommended when dealing with humans for practical purposes).
     */
    public static final int GRAIN_LENGTH_IN_MINUTES = 15;

    private int grainIndex; // unique

    private Day day;
    private int startTimeGrain;

    public int getGrainIndex() {
        return grainIndex;
    }

    public void setGrainIndex(int grainIndex) {
        this.grainIndex = grainIndex;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return day.toDate();
    }

    public LocalTime getTime() {
        return LocalTime.of(startTimeGrain / 60, startTimeGrain % 60);
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(getDate(), getTime());
    }
    
    

    public int getStartTimeGrain() {
		return startTimeGrain;
	}

	public void setStartTimeGrain(int startTimeGrain) {
		this.startTimeGrain = startTimeGrain;
	}


	public String getTimeString() {
        int hourOfDay = startTimeGrain / 60;
        int minuteOfHour = startTimeGrain % 60;
        return (hourOfDay < 10 ? "0" : "") + hourOfDay
                + ":" + (minuteOfHour < 10 ? "0" : "") + minuteOfHour;
    }

    public String getDateTimeString() {
        return day.getDateString() + " " + getTimeString();
    }


    @Override
    public String toString() {
        return grainIndex + "(" + getDateTimeString() + ")";
    }
}