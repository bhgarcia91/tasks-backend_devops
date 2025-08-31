package br.ce.bruno.taskbackend.controller;

import br.ce.bruno.taskbackend.model.Task;
import br.ce.bruno.taskbackend.repo.TaskRepo;
import br.ce.bruno.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController taskController;

    @Before
    public void setup(){

    }

    @Test
    public void naoSalvaTarefaSemDescricao(){
            Task todo = new Task();
            todo.setDueDate(LocalDate.of(2026, 1, 1));

            try{
                taskController.save(todo);
            } catch  (ValidationException e) {

                Assert.assertEquals("Fill the task description", e.getMessage());
            }


    }

    @Test
    public void naoSalvaTarefaSemData(){
        Task todo = new Task();
        todo.setTask("Descrição");

        try{
            taskController.save(todo);
        } catch  (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naoSalvaTarefaComDataPassada(){
        Task todo = new Task();
        todo.setTask("Descrição");
        todo.setDueDate(LocalDate.of(2025, 1, 1));

        try{
            taskController.save(todo);
            Assert.fail();
        } catch  (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void salvaTarefacorretamente() throws  ValidationException {
        Task todo = new Task();
        todo.setTask("Descrição");
        todo.setDueDate(LocalDate.of(2026, 1, 1));

        taskController.save(todo);
    }

}
