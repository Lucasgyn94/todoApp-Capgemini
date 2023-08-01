package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class TaskController {

    public void save(Task task) {
        
        String sql = "INSERT INTO tasks (idProject, "
                + "name, "
                + "description, "
                + "completed, "
                + "notes, "
                + "deadline, "
                + "createdAt, "
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new java.sql.Date(task.getDeadLine().getTime()));
            statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa! "
                + ex.getMessage(), ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    public void update(Task task) {
        
//        UPDATE `tasks` SET 
//                `id`='[value-1]',
//                `idProject`='[value-2]',
//                `name`='[value-3]',
//                `description`='[value-4]',
//                `completed`='[value-5]',
//                `notes`='[value-6]',
//                `deadline`='[value-7]',
//                `createdAt`='[value-8]',
//                `updatedAt`='[value-9]' WHERE 1
        
        String  sql =   "UPDATE tasks SET "
                    +   "idProject = ?, "
                    +   "name = ?, "
                    +   "description = ?, "
                    +   "completed = ?, "
                    +   "notes = ?, "
                    +   "deadline = ?, "
                    +   "createdAt = ?, "
                    +   "updatedAt = ? "
                    +   "WHERE id = ?"
                ;
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            
            // estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            // preparando a query
            statement = connection.prepareStatement(sql);
     
            // setando os valores no stament
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadLine().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
//            statement.setInt(1, task.getId());
//            statement.setInt(2, task.getIdProject());
//            statement.setString(3, task.getName());
//            statement.setString(4, task.getDescription());
//            statement.setString(5, task.getNotes());
//            statement.setBoolean(6, task.isIsCompleted());
//            statement.setDate(7, new Date(task.getDeadLine().getTime()));
//            statement.setDate(8, new Date(task.getCreatedAt().getTime()));
//            statement.setDate(9, new Date(task.getUpdatedAt().getTime()));
//            
            // executando a querye
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa. "
                + ex.getMessage(), ex);
        } 
    }
    
    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            // estabelecendo conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            // preparando a querye
            statement = connection.prepareStatement(sql);
            
            // setando os valores
            statement.setInt(1, taskId);
            
            // executando a querye
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa"
                + ex.getMessage(), ex);     
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject) {
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        // criando uma lista de tarefas
        List<Task> tasks = new ArrayList<>();
        
        try {
            // criação da conexão cm o banco de dados
            connection = ConnectionFactory.getConnection();
            
            // preparando a querye
            statement = connection.prepareStatement(sql);
            
            // setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            
            // valor retornado pela execução da querye
            resultSet = statement.executeQuery();
            
            // enquanto houverem valores a serem percorridos no meu resultSet
            while (resultSet.next()) {
                
                Task task = new Task();
                
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadLine(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                tasks.add(task);
            }
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao Inserir a tarefa"
                + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        
        // retornando a lista de tarefas q foi criada e carrega do banco de dados
        return tasks;
    }
}
