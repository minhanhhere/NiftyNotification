package com.gitonway.lee.niftynotification.lib;
/*
 * Copyright 2014 gitonway
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

public class Configuration {

    public static final int ANIM_DURATION= 200;

    public static final int ANIM_DISPLAY_DURATION= 5000;

    final long animDuration;

    final long dispalyDuration;

    final String textColor;

    final String backgroundColor;

    private Configuration(final Builder builder) {
        this.animDuration=builder.animDuration;
        this.textColor=builder.textColor;
        this.dispalyDuration=builder.dispalyDuration;
        this.backgroundColor=builder.backgroundColor;
    }

    public static class Builder {

        private long animDuration;

        private long dispalyDuration;

        private String textColor;

        private String backgroundColor;

        public Builder() {
            animDuration=ANIM_DURATION;
            dispalyDuration=ANIM_DISPLAY_DURATION;
            textColor="#FFFFFFFF";
            backgroundColor="#FFE91E63";
        }

        public Builder(final Configuration baseStyle) {
            animDuration=baseStyle.animDuration;
            textColor=baseStyle.textColor;
            backgroundColor=baseStyle.backgroundColor;
        }

        public Builder setAnimDuration(long animDuration){
            this.animDuration=animDuration;
            return this;
        }

        public Builder setDispalyDuration(long dispalyDuration){
            this.dispalyDuration=dispalyDuration;
            return this;
        }

        public Builder setTextColor(String textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setBackgroundColor(String backgroundColor){
            this.backgroundColor=backgroundColor;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
