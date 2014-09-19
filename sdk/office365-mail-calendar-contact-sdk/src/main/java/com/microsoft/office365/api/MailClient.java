package com.microsoft.office365.api;

import com.microsoft.office365.microsoft.exchange.services.odata.model.types.FileAttachment;
import com.microsoft.office365.microsoft.exchange.services.odata.model.types.Folder;
import com.microsoft.office365.microsoft.exchange.services.odata.model.types.FolderCollection;
import com.microsoft.office365.microsoft.exchange.services.odata.model.types.Item;
import com.microsoft.office365.microsoft.exchange.services.odata.model.types.ItemAttachment;
import com.microsoft.office365.microsoft.exchange.services.odata.model.types.Message;
import com.microsoft.office365.microsoft.exchange.services.odata.model.types.MessageCollection;
import com.microsoft.office365.oauth.OAuthCredentials;

/**
 * The Class MailClient.
 */
public class MailClient extends BaseOfficeClient {

    Builder mBuilder;

    protected MailClient(MailClient.Builder builder) {
        super(builder);
        mBuilder = builder;
    }

    /**
     * Create message.
     *
     * @return the message
     */
    public Message createMessage() {

        return getEntityContainer().newEntityInstance(Message.class);
    }

    /**
     * Gets a Message with a given folder id and message id.
     *
     * @param folderId  the folder id
     * @param messageId the message id
     * @return the message
     */
    public Message getMessage(String folderId, String messageId) {


        Folder folder = getEntityContainer().getMe().getFolders().getByKey(folderId);

        if (folder == null) {
            throw new UnsupportedOperationException("Folder cannot be null");
        }

        return folder.getMessages().getByKey(messageId);
    }

    /**
     * Sends a Message.
     *
     * @param message the message
     */
    public void sendMessage(Message message) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        message.operations().send().execute();
    }

    /**
     * Creates the reply.
     *
     * @param message the message
     * @return the message
     */
    public Message createReply(Message message) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        return message.operations().createReply().execute();
    }

    /**
     * Creates the reply all.
     *
     * @param message the message
     * @return the message
     */
    public Message createReplyAll(Message message) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        return message.operations().createReply().execute();
    }

    /**
     * Reply.
     *
     * @param messageId the message id
     * @param comment   the comment
     */
    public void reply(String messageId, String comment) {

        Message message = getEntityContainer().getMe().getMessages().getByKey(messageId);

        if (message == null) {
            throw new UnsupportedOperationException("Message cannot be null");
        }

        message.operations().reply(comment).execute();

    }

    /**
     * Reply.
     *
     * @param message the message
     * @param comment the comment
     */
    public void reply(Message message, String comment) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        message.operations().reply(comment).execute();
    }

    /**
     * Reply all.
     *
     * @param messageId the message id
     * @param comment   the comment
     */
    public void replyAll(String messageId, String comment) {

        Message message = getEntityContainer().getMe().getMessages().getByKey(messageId);

        if (message == null) {
            throw new UnsupportedOperationException("Message cannot be null");
        }

        message.operations().replyAll(comment).execute();
    }

    /**
     * Reply all.
     *
     * @param message the message
     * @param comment the comment
     */
    public void replyAll(Message message, String comment) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        message.operations().replyAll(comment);
    }

    /**
     * Creates the file attachment.
     *
     * @param message the message
     * @param name    the FileAttachment name
     * @param data    the data
     * @return the file attachment
     */
    public FileAttachment addAttachment(Message message, String name, byte[] data) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("Attachment name cannot be null");
        }

        if (data == null) {
            throw new IllegalArgumentException("Attachment data cannot be null");
        }

        FileAttachment fileAttachment = getEntityContainer().newEntityInstance(FileAttachment.class);
        fileAttachment.setContentBytes(data);
        fileAttachment.setName(name);

        message.getAttachments().add(fileAttachment);
        return fileAttachment;
    }

    /**
     * Creates the item attachment.
     *
     * @param message the message
     * @param name    the ItemAttachment name
     * @param item    the ItemAttachment
     * @return the ItemAttachment
     */
    public ItemAttachment addAttachment(Message message, String name, Item item) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("Attachment name cannot be null");
        }

        ItemAttachment itemAttachment = getEntityContainer().newEntityInstance(ItemAttachment.class);
        itemAttachment.setItem(item);
        itemAttachment.setName(name);

        message.getAttachments().add(itemAttachment);
        return itemAttachment;
    }

    /**
     * Move.
     *
     * @param message           the message
     * @param destinationFolder the destination folder
     */
    public Message moveMessage(Message message, Folder destinationFolder) {

        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        if (destinationFolder == null) {
            throw new IllegalArgumentException("destinationFolder cannot be null");
        }

        return message.operations().move(destinationFolder.getId()).execute();
    }

    /**
     * Move Message.
     *
     * @param messageId           the message
     * @param destinationFolderId the destination folder
     */
    public Message moveMessage(String messageId, String destinationFolderId) {

        if (messageId == null) {
            throw new IllegalArgumentException("messageId cannot be null");
        }

        if (destinationFolderId == null) {
            throw new IllegalArgumentException("destinationFolderId cannot be null");
        }

        Message message = getEntityContainer().getMe().getMessages().getByKey(messageId);

        if (message == null) {
            throw new UnsupportedOperationException("Invalid message");
        }
        return message.operations().move(destinationFolderId).execute();
    }

    /**
     * Copy Message.
     *
     * @param messageId           the message
     * @param destinationFolderId the destination folder
     */
    public Message copyMessage(String messageId, String destinationFolderId) {

        if (messageId == null) {
            throw new IllegalArgumentException("messageId cannot be null");
        }

        if (destinationFolderId == null) {
            throw new IllegalArgumentException("destinationFolderId cannot be null");
        }

        Message message = getEntityContainer().getMe().getMessages().getByKey(messageId);

        if (message == null) {
            throw new UnsupportedOperationException("Invalid message");
        }

        return message.operations().copy(destinationFolderId).execute();
    }

    /**
     * Delete Message.
     *
     * @param messageId the message
     */
    public void deleteMessage(String messageId) {

        if (messageId == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        getEntityContainer().getMe().getMessages().delete(messageId);
    }

    /**
     * Creates a Folder with given folder name.
     *
     * @param parentFolderId the parent folder id
     * @param folderName     the folder name
     */
    public Folder createFolder(String parentFolderId, String folderName) {

        Folder newFolder = getEntityContainer().newEntityInstance(Folder.class);
        newFolder.setDisplayName(folderName);

        Folder parentFolder = getEntityContainer().getMe().getFolders().getByKey(parentFolderId);

        if (parentFolder == null) {
            throw new UnsupportedOperationException("Parent folder cannot be null");
        }

        parentFolder.getChildFolders().add(newFolder);
        getEntityContainer().flush();
        return newFolder;
    }

    /**
     * Move Folder.
     *
     * @param folder         the message
     * @param parentFolderId the destination folder
     */
    public Folder moveFolder(Folder folder, String parentFolderId) {

        if (folder == null) {
            throw new IllegalArgumentException("folder cannot be null");
        }

        if (parentFolderId == null) {
            throw new IllegalArgumentException("parentFolderId cannot be null");
        }

        return folder.operations().move(parentFolderId).execute();
    }

    /**
     * Deletes a Folder with given folder Id.
     *
     * @param folderId the message
     */
    public void deleteFolder(String folderId) {

        getEntityContainer().getMe().getFolders().getByKey(folderId).delete();

    }

    /**
     * Gets the messages.
     *
     * @param folderId the folder id
     * @return the messages
     */
    public MessageCollection getMessages(String folderId) {

        return getMessages(folderId, null, null, 0, 0);
    }

    /**
     * Gets the messages.
     *
     * @param folderId the folder id
     * @param skip     the from
     * @return the messages
     */
    public MessageCollection getMessages(String folderId, int skip) {

      /*  MessageCollection messages = null;

        try {

            messages = getEntityContainer().getMe().getFolders().getByKey(folderId).getMessages()
                    .select("Id,From,Sender,Subject,BodyPreview,DateTimeSent,LastModifiedTime")
                    .expand("From,Sender")
                    .skip(from)
                    .top(mBuilder.getMaxDefaultResults()).execute();

        } catch (Throwable t) {
            //Log.e("Client", t.getMessage());
        }

        return messages;*/
        return getMessages(folderId, null, null, skip, 0);
    }

    /**
     * Gets the messages.
     *
     * @param folderId the folder id
     * @param skip     the from
     * @param top      the top
     * @return the messages
     */
    public MessageCollection getMessages(String folderId, int skip, int top) {
        return getMessages(folderId, null, null, skip, top);
    }

    /**
     * Gets the messages.
     *
     * @param folderId     the folder id
     * @param selectClause the select clause
     * @return the messages
     */
    public MessageCollection getMessages(String folderId, String selectClause) {
        return getMessages(folderId, selectClause, null, 0, 0);
    }

    /**
     * Gets the messages.
     *
     * @param folderId     the folder id
     * @param selectClause the select clause
     * @param expandClause the expand clause
     * @return the messages
     */
    public MessageCollection getMessages(String folderId, String selectClause,
                                         String expandClause) {
        return getMessages(folderId, selectClause, expandClause, 0, 0);
    }

    /**
     * Gets the messages.
     *
     * @param folderId     the folder id
     * @param selectClause the select clause
     * @param expandClause the expand clause
     * @param skip         number of records to skip
     * @param top          number of records to return
     * @return the messages
     */
    public MessageCollection getMessages(String folderId, String selectClause,
                                         String expandClause, int skip, int top) {

        if (folderId == null) {
            throw new IllegalArgumentException("FolderId cannot be null");
        }

        Folder folder = getEntityContainer().getMe().getFolders().getByKey(folderId);

        if (folder == null) {
            throw new UnsupportedOperationException("Folder cannot be null");
        }

        Folder.Messages messages = folder.getMessages();

        if (messages == null) {
            throw new IllegalStateException("Messages cannot be null");
        }

        if (selectClause != null) {
            messages = messages.select(selectClause);

            if (expandClause != null) {
                messages = messages.expand(expandClause);
            }
            messages = addSkipAndTop(skip, top, messages);

        } else {
            messages = addSkipAndTop(skip, top, messages);
        }

        return messages.execute();
    }

    //TODO;
    public FolderCollection getChildFolders() {

        FolderCollection childFolders = getEntityContainer().getMe()
                                                            .getRootFolder().getChildFolders().execute();
        return childFolders;
    }

    private Folder.Messages addSkipAndTop(int skip, int topResults, Folder.Messages messages) {

        if (messages == null) {
            throw new IllegalStateException("Messages cannot be null");
        }

        if (skip > 0) {
            messages = messages.skip(skip);
        }

        if (topResults > 0) {
            messages = messages.top(topResults);
        } else {
            messages = messages.top(mBuilder.getMaxDefaultResults());
        }
        return messages;
    }

    /**
     * The Class Builder.
     */
    public static final class Builder extends BaseOfficeClient.Builder {

        private int mMaxResults;

        /**
         * Instantiates a new builder.
         */
        public Builder() {
            super();
        }

        /**
         * Instantiates a new builder.
         *
         * @param credentials   the credentials
         * @param resourceId    the resource id
         * @param odataEndpoint the odata endpoint
         */
        public Builder(OAuthCredentials credentials, String resourceId, String odataEndpoint) {
            super(credentials, resourceId, odataEndpoint);
        }

        /*
         * (non-Javadoc)
         *
         * @see com.microsoft.office365.api.BaseOfficeClient.Builder#build()
         */
        @Override
        public MailClient build() {
            // TODO:Singleton?
            return new MailClient(this);
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * com.microsoft.office365.api.BaseOfficeClient.Builder#setCredentials
         * (com.microsoft.office365.http.OAuthCredentials)
         */
        @Override
        public Builder setCredentials(OAuthCredentials credentials) {
            return (Builder) super.setCredentials(credentials);
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * com.microsoft.office365.api.BaseOfficeClient.Builder#setOdataEndpoint
         * (java.lang.String)
         */
        @Override
        public Builder setODataEndpoint(String odataEndpoint) {
            return (Builder) super.setODataEndpoint(odataEndpoint);
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * com.microsoft.office365.api.BaseOfficeClient.Builder#setResourceId
         * (java.lang.String)
         */
        @Override
        public Builder setResourceId(String resourceId) {
            return (Builder) super.setResourceId(resourceId);
        }

        /**
         * Sets the max results.
         *
         * @param maxResults the max results
         * @return the builder
         */
        public Builder setMaxDefaultResults(int maxResults) {
            mMaxResults = maxResults;
            return this;
        }

        /**
         * Gets the max results.
         *
         * @return the max results
         */
        public int getMaxDefaultResults() {
            return mMaxResults;
        }
    }
}
