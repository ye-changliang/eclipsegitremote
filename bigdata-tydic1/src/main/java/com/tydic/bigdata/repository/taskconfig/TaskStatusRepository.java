package com.tydic.bigdata.repository.taskconfig;

import com.tydic.bigdata.domain.taskconfig.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

}
