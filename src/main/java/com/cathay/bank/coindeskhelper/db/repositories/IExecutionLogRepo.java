package com.cathay.bank.coindeskhelper.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cathay.bank.coindeskhelper.db.entities.ExecutionLog;

public interface IExecutionLogRepo extends JpaRepository<ExecutionLog, Long> {

}
