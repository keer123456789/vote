package com.keer.vote.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteInfo {
    private List<VoteInfoLabel> zx;
    private List<VoteInfoLabel> mg;
    private List<VoteInfoLabel> cx;
    private List<VoteInfoLabel> kj;
    private List<VoteInfoLabel> tj;

    public List<VoteInfoLabel> getZx() {
        return zx;
    }

    public void setZx(List<VoteInfoLabel> zx) {
        this.zx = zx;
    }

    public List<VoteInfoLabel> getMg() {
        return mg;
    }

    public void setMg(List<VoteInfoLabel> mg) {
        this.mg = mg;
    }

    public List<VoteInfoLabel> getCx() {
        return cx;
    }

    public void setCx(List<VoteInfoLabel> cx) {
        this.cx = cx;
    }

    public List<VoteInfoLabel> getKj() {
        return kj;
    }

    public void setKj(List<VoteInfoLabel> kj) {
        this.kj = kj;
    }

    public List<VoteInfoLabel> getTj() {
        return tj;
    }

    public void setTj(List<VoteInfoLabel> tj) {
        this.tj = tj;
    }

    public void vote(String award, String username) {
        vote(award, username, 1);
    }

    public void vote(String award, String username, int num) {
        if (award.equals("zx")) {
            voteZx(username, num);
        } else if (award.equals("mg")) {
            voteMg(username, num);
        } else if (award.equals("cx")) {
            voteCx(username, num);
        } else if (award.equals("kj")) {
            voteKj(username, num);
        } else if (award.equals("tj")) {
            voteTj(username, num);
        }
    }

    private void voteZx(String username, int num) {
        if (this.zx == null) {
            this.zx = new ArrayList<>();
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.zx.add(label);
            return;
        }
        boolean flag = false;
        for (VoteInfoLabel label : this.zx) {
            if (label.getUsername().equals(username)) {
                label.add(num);
                flag = true;
                break;
            }
        }

        if (!flag) {
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.zx.add(label);
        }

    }

    private void voteMg(String username, int num) {
        if (this.mg == null) {
            this.mg = new ArrayList<>();
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.mg.add(label);
            return;
        }
        boolean flag = false;
        for (VoteInfoLabel label : this.mg) {
            if (label.getUsername().equals(username)) {
                label.add(num);
                flag = true;
                break;
            }
        }

        if (!flag) {
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.mg.add(label);
        }

    }

    private void voteCx(String username, int num) {
        if (this.cx == null) {
            this.cx = new ArrayList<>();
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.cx.add(label);
            return;
        }
        boolean flag = false;
        for (VoteInfoLabel label : this.cx) {
            if (label.getUsername().equals(username)) {
                label.add(num);
                flag = true;
                break;
            }
        }

        if (!flag) {
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.cx.add(label);
        }

    }

    private void voteKj(String username, int num) {
        if (this.kj == null) {
            this.kj = new ArrayList<>();
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.kj.add(label);
            return;
        }
        boolean flag = false;
        for (VoteInfoLabel label : this.kj) {
            if (label.getUsername().equals(username)) {
                label.add(num);
                flag = true;
                break;
            }
        }

        if (!flag) {
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.kj.add(label);
        }

    }

    private void voteTj(String username, int num) {
        if (this.tj == null) {
            this.tj = new ArrayList<>();
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.tj.add(label);
            return;
        }
        boolean flag = false;
        for (VoteInfoLabel label : this.tj) {
            if (label.getUsername().equals(username)) {
                label.add(num);
                flag = true;
                break;
            }
        }

        if (!flag) {
            VoteInfoLabel label = new VoteInfoLabel(username, 1);
            this.tj.add(label);
        }

    }

    public Map<String, String> resultInfo() {
        Map<String, String> result = new HashMap<>();
        int max = 0;
        String name = "";
        for (VoteInfoLabel label : this.zx) {
            if (label.getNum() >= max) {
                max = label.getNum();
                name = label.getUsername();
            }
        }
        result.put("zx", name);

        max = 0;
        name = "";
        for (VoteInfoLabel label : this.mg) {
            if (label.getNum() >= max) {
                max = label.getNum();
                name = label.getUsername();
            }
        }
        result.put("mg", name);

        max = 0;
        name = "";
        for (VoteInfoLabel label : this.cx) {
            if (label.getNum() >= max) {
                max = label.getNum();
                name = label.getUsername();
            }
        }
        result.put("cx", name);

        max = 0;
        name = "";
        for (VoteInfoLabel label : this.kj) {
            if (label.getNum() >= max) {
                max = label.getNum();
                name = label.getUsername();
            }
        }
        result.put("kj", name);

        max = 0;
        name = "";
        for (VoteInfoLabel label : this.tj) {
            if (label.getNum() >= max) {
                max = label.getNum();
                name = label.getUsername();
            }
        }
        result.put("tj", name);
        return result;
    }
}
