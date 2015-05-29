/*
 ** analytics data collection ( pv,uv browserType and clientType)
 *  by ssw
 *  2014-08-28
 */
//
(function () {
    var link="http://221.228.193.161/__bb.gif?";
    var browserType = function () {
        var a = navigator.userAgent.toLowerCase(), b = "-", ie = "msie", ch = "chrome", ff = "firefox", op = "opra",
            sf = "safari", p = -1, e;
        p = a.indexOf(ie);
        if (p != -1) {
            b = ie;
            e = pz(a, ";", p);
            b += su(a, p, e).split(" ")[1].split(".")[0];
            return b;
        }
        return pz(a, ff) != -1 ? ff : pz(a, ch) != -1 ? ch : pz(a, op) != -1 ? op : pz(a, sf) != -1 ? sf : b;
    }

    var pz = function (a, b, c) {
        return c && c != -1 ? a.indexOf(b, c) : a.indexOf(b);
    }
    var su = function (a, b, c) {
        return c && c != -1 ? a.substring(b, c) : a.substring(b);
    }

    var osInfo = function () {
        var unknown = '-';
        var os = unknown;
        var nAgt = navigator.appVersion;
        var nVer = navigator.userAgent;
        var clientStrings = [
            {s: 'Windows 3.11', r: /Win16/},
            {s: 'Windows 95', r: /(Windows 95|Win95|Windows_95)/},
            {s: 'Windows ME', r: /(Win 9x 4.90|Windows ME)/},
            {s: 'Windows 98', r: /(Windows 98|Win98)/},
            {s: 'Windows CE', r: /Windows CE/},
            {s: 'Windows 2000', r: /(Windows NT 5.0|Windows 2000)/},
            {s: 'Windows XP', r: /(Windows NT 5.1|Windows XP)/},
            {s: 'Windows Server 2003', r: /Windows NT 5.2/},
            {s: 'Windows Vista', r: /Windows NT 6.0/},
            {s: 'Windows 7', r: /(Windows 7|Windows NT 6.1)/},
            {s: 'Windows 8.1', r: /(Windows 8.1|Windows NT 6.3)/},
            {s: 'Windows 8', r: /(Windows 8|Windows NT 6.2)/},
            {s: 'Windows NT 4.0', r: /(Windows NT 4.0|WinNT4.0|WinNT|Windows NT)/},
            {s: 'Windows ME', r: /Windows ME/},
            {s: 'Android', r: /Android/},
            {s: 'Open BSD', r: /OpenBSD/},
            {s: 'Sun OS', r: /SunOS/},
            {s: 'Linux', r: /(Linux|X11)/},
            {s: 'iOS', r: /(iPhone|iPad|iPod)/},
            {s: 'Mac OS X', r: /Mac OS X/},
            {s: 'Mac OS', r: /(MacPPC|MacIntel|Mac_PowerPC|Macintosh)/},
            {s: 'QNX', r: /QNX/},
            {s: 'UNIX', r: /UNIX/},
            {s: 'BeOS', r: /BeOS/},
            {s: 'OS/2', r: /OS\/2/},
            {s: 'Search Bot', r: /(nuhk|Googlebot|Yammybot|Openbot|Slurp|MSNBot|Ask Jeeves\/Teoma|ia_archiver)/}
        ];
        for (var id in clientStrings) {
            var cs = clientStrings[id];
            if (cs.r.test(nAgt)) {
                os = cs.s;
                break;
            }
        }
        var osVersion = unknown;
        if (/Windows/.test(os)) {
            osVersion = /Windows (.*)/.exec(os)[1];
            os = 'Windows';
        }
        switch (os) {
            case 'Mac OS X':
                osVersion = /Mac OS X (10[\.\_\d]+)/.exec(nAgt)[1];
                break;

            case 'Android':
                osVersion = /Android ([\.\_\d]+)/.exec(nAgt)[1];
                break;

            case 'iOS':
                osVersion = /OS (\d+)_(\d+)_?(\d+)?/.exec(nVer);
                osVersion = osVersion[1] + '.' + osVersion[2] + '.' + (osVersion[3] | 0);
                break;
        }
        window.jscd = {
            os: os,
            osVersion: osVersion
        };
        return  jscd.os + '' + jscd.osVersion
    }

    args="browserType="+browserType()+"&clientType="+osInfo()+"&referrer="+document.referrer;
    var img = new Image(1, 1);
    img.src = link + args+"&t="+new Date().getTime();
})();