package vn.uit.nt213.m21.team8.file_manager.Model;

public class ChangePassResponse {
    private String title;
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChangePassResponse(String title, String status) {
        this.title = title;
        this.status = status;
    }
}
