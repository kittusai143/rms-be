package com.sentrifugo.performanceManagement.vo;


import java.util.List;

public class DistinctData {
    List<Integer> managers;
    List<String> clients;
    List<String> projects;

    public List<Integer> getManagers() {
        return managers;
    }

    public void setManagers(List<Integer> managers) {
        this.managers = managers;
    }

    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }
}
