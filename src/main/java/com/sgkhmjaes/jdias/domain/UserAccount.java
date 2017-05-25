package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserAccount.
 */
@Entity
@Table(name = "user_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "useraccount")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "serialized_private_key")
    private String serializedPrivateKey;

    @Column(name = "getting_started")
    private Boolean gettingStarted;

    @Column(name = "disable_mail")
    private Boolean disableMail;

    @Column(name = "language")
    private String language;

    @Column(name = "remember_created_at")
    private ZonedDateTime rememberCreatedAt;

    @Column(name = "sign_in_count")
    private Integer signInCount;

    @Column(name = "current_sign_in_at")
    private ZonedDateTime currentSignInAt;

    @Column(name = "last_sign_in_at")
    private ZonedDateTime lastSignInAt;

    @Column(name = "current_sign_in_ip")
    private String currentSignInIp;

    @Column(name = "last_sign_in_ip")
    private String lastSignInIp;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "locked_at")
    private ZonedDateTime lockedAt;

    @Column(name = "show_community_spotlight_in_stream")
    private Boolean showCommunitySpotlightInStream;

    @Column(name = "auto_follow_back")
    private Boolean autoFollowBack;

    @Column(name = "auto_follow_back_aspect_id")
    private Integer autoFollowBackAspectId;

    @Column(name = "hidden_shareables")
    private String hiddenShareables;

    @Column(name = "last_seen")
    private ZonedDateTime lastSeen;

    @Column(name = "export_e")
    private String exportE;

    @Column(name = "exported_at")
    private ZonedDateTime exportedAt;

    @Column(name = "exporting")
    private Boolean exporting;

    @Column(name = "strip_exif")
    private Boolean stripExif;

    @Column(name = "exported_photos_file")
    private String exportedPhotosFile;

    @Column(name = "exported_photos_at")
    private ZonedDateTime exportedPhotosAt;

    @Column(name = "exporting_photos")
    private Boolean exportingPhotos;

    @Column(name = "color_theme")
    private String colorTheme;

    @Column(name = "post_default_public")
    private Boolean postDefaultPublic;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @OneToMany(mappedBy = "userAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Conversation> conversations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerializedPrivateKey() {
        return serializedPrivateKey;
    }

    public UserAccount serializedPrivateKey(String serializedPrivateKey) {
        this.serializedPrivateKey = serializedPrivateKey;
        return this;
    }

    public void setSerializedPrivateKey(String serializedPrivateKey) {
        this.serializedPrivateKey = serializedPrivateKey;
    }

    public Boolean isGettingStarted() {
        return gettingStarted;
    }

    public UserAccount gettingStarted(Boolean gettingStarted) {
        this.gettingStarted = gettingStarted;
        return this;
    }

    public void setGettingStarted(Boolean gettingStarted) {
        this.gettingStarted = gettingStarted;
    }

    public Boolean isDisableMail() {
        return disableMail;
    }

    public UserAccount disableMail(Boolean disableMail) {
        this.disableMail = disableMail;
        return this;
    }

    public void setDisableMail(Boolean disableMail) {
        this.disableMail = disableMail;
    }

    public String getLanguage() {
        return language;
    }

    public UserAccount language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ZonedDateTime getRememberCreatedAt() {
        return rememberCreatedAt;
    }

    public UserAccount rememberCreatedAt(ZonedDateTime rememberCreatedAt) {
        this.rememberCreatedAt = rememberCreatedAt;
        return this;
    }

    public void setRememberCreatedAt(ZonedDateTime rememberCreatedAt) {
        this.rememberCreatedAt = rememberCreatedAt;
    }

    public Integer getSignInCount() {
        return signInCount;
    }

    public UserAccount signInCount(Integer signInCount) {
        this.signInCount = signInCount;
        return this;
    }

    public void setSignInCount(Integer signInCount) {
        this.signInCount = signInCount;
    }

    public ZonedDateTime getCurrentSignInAt() {
        return currentSignInAt;
    }

    public UserAccount currentSignInAt(ZonedDateTime currentSignInAt) {
        this.currentSignInAt = currentSignInAt;
        return this;
    }

    public void setCurrentSignInAt(ZonedDateTime currentSignInAt) {
        this.currentSignInAt = currentSignInAt;
    }

    public ZonedDateTime getLastSignInAt() {
        return lastSignInAt;
    }

    public UserAccount lastSignInAt(ZonedDateTime lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
        return this;
    }

    public void setLastSignInAt(ZonedDateTime lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
    }

    public String getCurrentSignInIp() {
        return currentSignInIp;
    }

    public UserAccount currentSignInIp(String currentSignInIp) {
        this.currentSignInIp = currentSignInIp;
        return this;
    }

    public void setCurrentSignInIp(String currentSignInIp) {
        this.currentSignInIp = currentSignInIp;
    }

    public String getLastSignInIp() {
        return lastSignInIp;
    }

    public UserAccount lastSignInIp(String lastSignInIp) {
        this.lastSignInIp = lastSignInIp;
        return this;
    }

    public void setLastSignInIp(String lastSignInIp) {
        this.lastSignInIp = lastSignInIp;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public UserAccount createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserAccount updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getLockedAt() {
        return lockedAt;
    }

    public UserAccount lockedAt(ZonedDateTime lockedAt) {
        this.lockedAt = lockedAt;
        return this;
    }

    public void setLockedAt(ZonedDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }

    public Boolean isShowCommunitySpotlightInStream() {
        return showCommunitySpotlightInStream;
    }

    public UserAccount showCommunitySpotlightInStream(Boolean showCommunitySpotlightInStream) {
        this.showCommunitySpotlightInStream = showCommunitySpotlightInStream;
        return this;
    }

    public void setShowCommunitySpotlightInStream(Boolean showCommunitySpotlightInStream) {
        this.showCommunitySpotlightInStream = showCommunitySpotlightInStream;
    }

    public Boolean isAutoFollowBack() {
        return autoFollowBack;
    }

    public UserAccount autoFollowBack(Boolean autoFollowBack) {
        this.autoFollowBack = autoFollowBack;
        return this;
    }

    public void setAutoFollowBack(Boolean autoFollowBack) {
        this.autoFollowBack = autoFollowBack;
    }

    public Integer getAutoFollowBackAspectId() {
        return autoFollowBackAspectId;
    }

    public UserAccount autoFollowBackAspectId(Integer autoFollowBackAspectId) {
        this.autoFollowBackAspectId = autoFollowBackAspectId;
        return this;
    }

    public void setAutoFollowBackAspectId(Integer autoFollowBackAspectId) {
        this.autoFollowBackAspectId = autoFollowBackAspectId;
    }

    public String getHiddenShareables() {
        return hiddenShareables;
    }

    public UserAccount hiddenShareables(String hiddenShareables) {
        this.hiddenShareables = hiddenShareables;
        return this;
    }

    public void setHiddenShareables(String hiddenShareables) {
        this.hiddenShareables = hiddenShareables;
    }

    public ZonedDateTime getLastSeen() {
        return lastSeen;
    }

    public UserAccount lastSeen(ZonedDateTime lastSeen) {
        this.lastSeen = lastSeen;
        return this;
    }

    public void setLastSeen(ZonedDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getExportE() {
        return exportE;
    }

    public UserAccount exportE(String exportE) {
        this.exportE = exportE;
        return this;
    }

    public void setExportE(String exportE) {
        this.exportE = exportE;
    }

    public ZonedDateTime getExportedAt() {
        return exportedAt;
    }

    public UserAccount exportedAt(ZonedDateTime exportedAt) {
        this.exportedAt = exportedAt;
        return this;
    }

    public void setExportedAt(ZonedDateTime exportedAt) {
        this.exportedAt = exportedAt;
    }

    public Boolean isExporting() {
        return exporting;
    }

    public UserAccount exporting(Boolean exporting) {
        this.exporting = exporting;
        return this;
    }

    public void setExporting(Boolean exporting) {
        this.exporting = exporting;
    }

    public Boolean isStripExif() {
        return stripExif;
    }

    public UserAccount stripExif(Boolean stripExif) {
        this.stripExif = stripExif;
        return this;
    }

    public void setStripExif(Boolean stripExif) {
        this.stripExif = stripExif;
    }

    public String getExportedPhotosFile() {
        return exportedPhotosFile;
    }

    public UserAccount exportedPhotosFile(String exportedPhotosFile) {
        this.exportedPhotosFile = exportedPhotosFile;
        return this;
    }

    public void setExportedPhotosFile(String exportedPhotosFile) {
        this.exportedPhotosFile = exportedPhotosFile;
    }

    public ZonedDateTime getExportedPhotosAt() {
        return exportedPhotosAt;
    }

    public UserAccount exportedPhotosAt(ZonedDateTime exportedPhotosAt) {
        this.exportedPhotosAt = exportedPhotosAt;
        return this;
    }

    public void setExportedPhotosAt(ZonedDateTime exportedPhotosAt) {
        this.exportedPhotosAt = exportedPhotosAt;
    }

    public Boolean isExportingPhotos() {
        return exportingPhotos;
    }

    public UserAccount exportingPhotos(Boolean exportingPhotos) {
        this.exportingPhotos = exportingPhotos;
        return this;
    }

    public void setExportingPhotos(Boolean exportingPhotos) {
        this.exportingPhotos = exportingPhotos;
    }

    public String getColorTheme() {
        return colorTheme;
    }

    public UserAccount colorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
        return this;
    }

    public void setColorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    public Boolean isPostDefaultPublic() {
        return postDefaultPublic;
    }

    public UserAccount postDefaultPublic(Boolean postDefaultPublic) {
        this.postDefaultPublic = postDefaultPublic;
        return this;
    }

    public void setPostDefaultPublic(Boolean postDefaultPublic) {
        this.postDefaultPublic = postDefaultPublic;
    }

    public User getUser() {
        return user;
    }

    public UserAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Person getPerson() {
        return person;
    }

    public UserAccount person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public UserAccount conversations(Set<Conversation> conversations) {
        this.conversations = conversations;
        return this;
    }

    public UserAccount addConversations(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.setUserAccount(this);
        return this;
    }

    public UserAccount removeConversations(Conversation conversation) {
        this.conversations.remove(conversation);
        conversation.setUserAccount(null);
        return this;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccount userAccount = (UserAccount) o;
        if (userAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", serializedPrivateKey='" + getSerializedPrivateKey() + "'" +
            ", gettingStarted='" + isGettingStarted() + "'" +
            ", disableMail='" + isDisableMail() + "'" +
            ", language='" + getLanguage() + "'" +
            ", rememberCreatedAt='" + getRememberCreatedAt() + "'" +
            ", signInCount='" + getSignInCount() + "'" +
            ", currentSignInAt='" + getCurrentSignInAt() + "'" +
            ", lastSignInAt='" + getLastSignInAt() + "'" +
            ", currentSignInIp='" + getCurrentSignInIp() + "'" +
            ", lastSignInIp='" + getLastSignInIp() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", lockedAt='" + getLockedAt() + "'" +
            ", showCommunitySpotlightInStream='" + isShowCommunitySpotlightInStream() + "'" +
            ", autoFollowBack='" + isAutoFollowBack() + "'" +
            ", autoFollowBackAspectId='" + getAutoFollowBackAspectId() + "'" +
            ", hiddenShareables='" + getHiddenShareables() + "'" +
            ", lastSeen='" + getLastSeen() + "'" +
            ", exportE='" + getExportE() + "'" +
            ", exportedAt='" + getExportedAt() + "'" +
            ", exporting='" + isExporting() + "'" +
            ", stripExif='" + isStripExif() + "'" +
            ", exportedPhotosFile='" + getExportedPhotosFile() + "'" +
            ", exportedPhotosAt='" + getExportedPhotosAt() + "'" +
            ", exportingPhotos='" + isExportingPhotos() + "'" +
            ", colorTheme='" + getColorTheme() + "'" +
            ", postDefaultPublic='" + isPostDefaultPublic() + "'" +
            "}";
    }
}
