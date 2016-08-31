package com.onemeter.omm.onemm.data;

import java.io.Serializable;

public class Post implements Serializable {
    private String date;
    private String answernerId;
    private String questionerPhoto;
    private String price;
    private String questionerContent;
    private String listenCount;
    private String answernerPhoto;
    private String length;
    private String questionerId;
    private String voiceContent;
    private String payInfo;

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAnswernerId() {
        return this.answernerId;
    }

    public void setAnswernerId(String answernerId) {
        this.answernerId = answernerId;
    }

    public String getQuestionerPhoto() {
        return this.questionerPhoto;
    }

    public void setQuestionerPhoto(String questionerPhoto) {
        this.questionerPhoto = questionerPhoto;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuestionerContent() {
        return this.questionerContent;
    }

    public void setQuestionerContent(String questionerContent) {
        this.questionerContent = questionerContent;
    }

    public String getListenCount() {
        return this.listenCount;
    }

    public void setListenCount(String listenCount) {
        this.listenCount = listenCount;
    }

    public String getAnswernerPhoto() {
        return this.answernerPhoto;
    }

    public void setAnswernerPhoto(String answernerPhoto) {
        this.answernerPhoto = answernerPhoto;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getQuestionerId() {
        return this.questionerId;
    }

    public void setQuestionerId(String questionerId) {
        this.questionerId = questionerId;
    }

    public String getVoiceContent() {
        return this.voiceContent;
    }

    public void setVoiceContent(String voiceContent) {
        this.voiceContent = voiceContent;
    }
}
