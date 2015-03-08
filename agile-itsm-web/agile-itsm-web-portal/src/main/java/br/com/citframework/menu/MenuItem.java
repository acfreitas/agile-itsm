package br.com.citframework.menu;

import java.util.Collection;

public class MenuItem {

    private String description;
    private String path;
    private boolean rootLevel = false;
    private Collection<MenuItem> menuItens;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Collection<MenuItem> getMenuItens() {
        return menuItens;
    }

    public void setMenuItens(final Collection<MenuItem> menuItens) {
        this.menuItens = menuItens;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public boolean isRootLevel() {
        return rootLevel;
    }

    public void setRootLevel(final boolean rootLevel) {
        this.rootLevel = rootLevel;
    }

}
