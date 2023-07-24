package com.example.flipimage.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {



        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("tab_group_id")
        @Expose
        private String tabGroupId;
        @SerializedName("game_id")
        @Expose
        private String gameId;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("timer")
        @Expose
        private String timer;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("game_present")
        @Expose
        private String gamePresent;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("game_image")
        @Expose
        private List<GameImage> gameImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getTabGroupId() {
            return tabGroupId;
        }

        public void setTabGroupId(String tabGroupId) {
            this.tabGroupId = tabGroupId;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTimer() {
            return timer;
        }

        public void setTimer(String timer) {
            this.timer = timer;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGamePresent() {
            return gamePresent;
        }

        public void setGamePresent(String gamePresent) {
            this.gamePresent = gamePresent;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<GameImage> getGameImage() {
            return gameImage;
        }

        public void setGameImage(List<GameImage> gameImage) {
            this.gameImage = gameImage;
        }

    }

