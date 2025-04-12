package nbcc.restaurant.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nbcc.restaurant.entities.Menu;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class MenuItemDTO {


    private long id;
    private String name;
    private String description;

    public MenuItemDTO() {
    }

    public MenuItemDTO(long id, String name, String description, long menu_id) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
