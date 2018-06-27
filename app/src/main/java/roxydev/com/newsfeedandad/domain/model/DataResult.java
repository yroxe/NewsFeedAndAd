package roxydev.com.newsfeedandad.domain.model;

public class DataResult {
    private Object payload;
    private String error;
    private LoadingState loadingState;

    public DataResult(Object payload,String error,LoadingState loadingState){
        this.payload = payload;
        this.error = error;
        this.loadingState = loadingState;
    }

    public Object getPayload() {
        return payload;
    }

    public String getError() {
        return error;
    }

    public LoadingState getLoadingState() {
        return loadingState;
    }
}
