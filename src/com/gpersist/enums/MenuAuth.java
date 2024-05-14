package com.gpersist.enums;

public enum MenuAuth {
    Browse(1), New(2), Edit(4), Delete(8), Print(16), Export(32), Deal(64), Valid(128), InValid(256), ValidInValid(374),
    Check(512), UnCheck(1024), Modify(14), BrowseExport(33), Upload(2048), Special01(4096), Special02(8192),
    Special03(16384);

    private final int auth;

    private MenuAuth(int auth) {
        this.auth = auth;
    }

    public int getAuth() {
        return this.auth;
    }
}
