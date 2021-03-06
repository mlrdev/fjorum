package org.fjorum.model.service;

import org.fjorum.controller.form.TopicCreateForm;
import org.fjorum.model.entity.Category;
import org.fjorum.model.entity.Topic;
import org.fjorum.model.entity.User;

import java.util.Optional;

public interface TopicService extends EntityService<Topic> {

    Topic createNewTopic(TopicCreateForm form, User user);

    Topic createNewTopic(Category category, User user, String name);
}
