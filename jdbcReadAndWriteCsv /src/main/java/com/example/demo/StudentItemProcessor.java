package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

	private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

	@Override
	public Student process(final Student student) throws Exception {
		final String firstName = student.getStu_name().toUpperCase();
		log.info("Converting " + student.stu_name );
		return student;
	}

}
