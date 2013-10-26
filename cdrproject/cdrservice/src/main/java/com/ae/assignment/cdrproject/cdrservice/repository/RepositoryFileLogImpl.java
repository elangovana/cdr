package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.ae.assignment.cdrproject.cdrservice.model.FileLog;

@Repository

public class RepositoryFileLogImpl extends AbstractRepository<FileLog, String> implements RepositoryFileLog , Serializable{

}
