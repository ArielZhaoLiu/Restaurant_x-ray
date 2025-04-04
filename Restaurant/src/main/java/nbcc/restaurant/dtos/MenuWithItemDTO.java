package nbcc.restaurant.dtos;

import java.util.List;

public class MenuWithItemDTO {

    private long id;
    private String name;
    private String description;
    private List<MenuItemDTO> menuItems;

    public MenuWithItemDTO() {
    }

    public MenuWithItemDTO(long id, String name, String description, List<MenuItemDTO> menuItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menuItems = menuItems;
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

    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }
}
