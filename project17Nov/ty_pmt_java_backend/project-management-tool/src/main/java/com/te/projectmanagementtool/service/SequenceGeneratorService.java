package com.te.projectmanagementtool.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.te.projectmanagementtool.entity.IdSequence;

@Service
@Component
public class SequenceGeneratorService {

	@Autowired
	private MongoOperations mongoOperations;

	public Integer getSequenceNumber(String sequenceName) {
		Query query = new Query(Criteria.where("id").is(sequenceName));
		Update update = new Update().inc("seq", 1);

		IdSequence findAndModify = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true),
				IdSequence.class);

		return !Objects.isNull(findAndModify) ? findAndModify.getSeq() : 1;
	}
}
