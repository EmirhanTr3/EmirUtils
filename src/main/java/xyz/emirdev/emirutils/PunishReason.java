package xyz.emirdev.emirutils;

public class PunishReason {
    private String reason;
    private boolean silent = false;

    public PunishReason(String reason, String defaultReason) {
        if (reason != null && reason.startsWith("-s")) {
            reason = reason.replaceFirst("-s ?", "");
            this.silent = true;
        }

        if (reason == null || reason.isEmpty()) {
            reason = defaultReason;
        }

        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public boolean isSilent() {
        return silent;
    }
}
