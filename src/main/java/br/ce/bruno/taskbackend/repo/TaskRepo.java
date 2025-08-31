package br.ce.bruno.taskbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ce.bruno.taskbackend.model.Task;

public interface TaskRepo extends JpaRepository<Task, Long>{

}
