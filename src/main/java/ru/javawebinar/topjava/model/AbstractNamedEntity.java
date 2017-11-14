package ru.javawebinar.topjava.model;

import java.util.Comparator;

public abstract class AbstractNamedEntity extends AbstractBaseEntity implements Comparable<AbstractNamedEntity> {

    protected String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }



    @Override
    public int compareTo(AbstractNamedEntity o) {
        return this.getName().equalsIgnoreCase(o.getName()) ? o.getId() - this.getId()
                : this.getName().compareToIgnoreCase(o.getName());
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, '%s')", getClass().getName(), id, name);
    }
}