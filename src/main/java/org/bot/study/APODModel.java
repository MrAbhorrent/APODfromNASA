// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package org.bot.study;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class APODModel {
    private LocalDate date;
    private String copyright;
    private String mediaType;
    private String hdurl;
    private String serviceVersion;
    private String explanation;
    private String title;
    private String url;

    public APODModel(
            @JsonProperty("date")
            LocalDate date,
            @JsonProperty("copyright")
            String copyright,
            @JsonProperty("media_type")
            String mediaType,
            @JsonProperty("hdurl")
            String hdurl,
            @JsonProperty("service_version")
            String serviceVersion,
            @JsonProperty("explanation")
            String explanation,
            @JsonProperty("title")
            String title,
            @JsonProperty("url")
            String url
    ) {
        setDate(date);
        setCopyright(copyright);
        setMediaType(mediaType);
        setHdurl(hdurl);
        setServiceVersion(serviceVersion);
        setExplanation(explanation);
        setTitle(title);
        setUrl(url);
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate value) { this.date = value; }

    public String getCopyright() { return copyright; }
    public void setCopyright(String value) { this.copyright = value; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String value) { this.mediaType = value; }

    public String getHdurl() { return hdurl; }
    public void setHdurl(String value) { this.hdurl = value; }

    public String getServiceVersion() { return serviceVersion; }
    public void setServiceVersion(String value) { this.serviceVersion = value; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String value) { this.explanation = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String geturl() { return url; }
    public void setUrl(String value) { this.url = value; }
}
