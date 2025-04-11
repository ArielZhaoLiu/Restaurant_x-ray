package nbcc.restaurant.dtos;

public class MenuItemDTO {


    private long id;
    private String name;
    private String description;
    private long menu_id;

    public MenuItemDTO() {
    }

    public MenuItemDTO(long id, String name, String description, long menu_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menu_id = menu_id;
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

    public long getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(long menu_id) {
        this.menu_id = menu_id;
    }
}
