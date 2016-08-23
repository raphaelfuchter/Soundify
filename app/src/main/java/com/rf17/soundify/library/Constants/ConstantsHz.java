package com.rf17.soundify.library.Constants;

public enum ConstantsHz {

    a('a', 3000),
    b('b', 1088),
    c('c', 1152),
    d('d', 1300),
    e('e', 1400),
    f('f', 1500),
    g('g', 1600),
    h('h', 1700),
    i('i', 1800),
    j('j', 1900),
    k('k', 2000),
    l('l', 2100),
    m('m', 2200),
    n('n', 2300),
    o('o', 2400),
    p('p', 2500),
    q('q', 2600),
    r('r', 2700),
    s('s', 2800),
    t('t', 2900),
    u('u', 3000),
    v('v', 3100),
    w('w', 3200),
    x('x', 3300),
    y('y', 3400),
    z('z', 3500),
    A('A', 3600),
    B('B', 3700),
    C('C', 3800),
    D('D', 3900),
    E('E', 4000),
    F('F', 4100),
    G('G', 4200),
    H('H', 4300),
    I('I', 4400),
    J('J', 4500),
    K('K', 4600) ,
    L('L', 4700),
    M('M', 4800),
    N('N', 5000),
    O('O', 5100),
    P('P', 5200),
    Q('Q', 5300),
    R('R', 5400),
    S('S', 5500),
    T('T', 5600),
    U('U', 5700),
    V('V', 5800),
    W('W', 5900),
    X('X', 6000),
    Y('Y', 6100),
    Z('Z', 6200),
    NUM_0('0', 6300),
    NUM_1('1', 6400),
    NUM_2('2', 6500),
    NUM_3('3', 6600),
    NUM_4('4', 6700),
    NUM_5('5', 6800),
    NUM_6('6', 6900),
    NUM_7('7', 7000),
    NUM_8('8', 7100),
    NUM_9('9', 7200),
    BEGIN('<', 2500),
    END('>', 4500),
    TX_ERROR('@', 50),
    SEPARATOR('|', 10000); // used as a separator between repeated digits

    private char desc;
    private int hz;

    ConstantsHz(char desc, int hz) {
        this.desc = desc;
        this.hz = hz;
    }

    public char getDesc() {
        return this.desc;
    }

    public int getHz() {
        return this.hz;
    }

}
