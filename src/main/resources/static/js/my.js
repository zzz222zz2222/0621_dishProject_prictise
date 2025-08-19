function isNumStr(s) {
    if (s == null || s.length == 0)
        return false;

    for (var i = 0; i < s.length; i++) {
        var ch = s.charAt(i);
        if (!(ch >= '0' && ch <= '9'))
            return false;
    }
    return true;
}

function isPassStr(s) {
    if (s == null || s.length < 8 || s.length > 12) {
        return false;
    }
    var bNum = false, bLetter = false, bOther = false;
    for (var i = 0; i < s.length; i++) {
        var c = s.charAt(i);
        if (c >= '0' && c <= '9') {
            bNum = true;
        } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            bLetter = true;
        } else if (c == '@' || c == '#') {
            bOther = true;
        } else return false;
    }
    return bNum && bLetter && bOther;
}