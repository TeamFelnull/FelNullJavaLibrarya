package dev.felnull.fnjl.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 土方関係
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNDokataUtil {
    private static final Map<String, String> DOKATA = new HashMap<>();

    private static String getDokata(String string) {
        InputStream stream = FNDokataUtil.class.getResourceAsStream("/dokata/" + string);

        if (stream != null) {
            try {
                stream = FNDataUtil.unzipGz(stream);
                DOKATA.put(string, FNStringUtil.decodeBase64(new String(FNDataUtil.streamToByteArray(stream), StandardCharsets.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return DOKATA.get(string);
    }

    public static String getKusomamirede() {
        return getDokata("kusomamire");
    }

    public static String getYattaze() {
        return getDokata("yattaze");
    }

    public static String getYaritai() {
        return getDokata("yaritai");
    }

    public static String getSenzuri() {
        return getDokata("senzuri");
    }

    public static String getKusoKusoKuso() {
        return getDokata("kusokusokuso");
    }

    public static String getKusomamire() {
        return getDokata("kusomamirede");
    }

    public static String getKusozukiOyajiJisamaBoshu() {
        return getDokata("kusozukioyajijisamaboshu");
    }

    public static String getKusomamirede2() {
        return getDokata("kusomamirede2");
    }

    public static String getHayakuYariMakuriTaiyo() {
        return getDokata("hayakuyarimakuritaiyo");
    }

    public static String getHayakuKusoMamireni() {
        return getDokata("hayakukusomamireni");
    }

    public static String getKusomamire2() {
        return getDokata("kusomamire2");
    }

    public static String getKusomamire3() {
        return getDokata("kusomamire3");
    }

    public static String getKindanSyouJoga() {
        return getDokata("kindansyoujoga");
    }

    public static String getKusoOyaji() {
        return getDokata("kusooyaji");
    }

    public static String getYarouze() {
        return getDokata("yarouze");
    }

    public static String getYagaiDe() {
        return getDokata("yagaide");
    }

    public static String getKusomamire4() {
        return getDokata("kusomamire4");
    }

    public static String getYaritexe() {
        return getDokata("yaritexe");
    }

    public static String getKusomamirede3() {
        return getDokata("kusomamirede3");
    }

    public static String getKusomamire5() {
        return getDokata("kusomamire5");
    }

    public static String getKuso() {
        return getDokata("kuso");
    }

    public static String getKusoDaisuki() {
        return getDokata("kusodaisuki");
    }

    public static String getYamaNoNakaDe() {
        return getDokata("yamanonakade");
    }

    public static String getYamaNoNakaDe2() {
        return getDokata("yamanonakade2");
    }

    public static String getYamaNoNakaDe3() {
        return getDokata("yamanonakade3");
    }

    public static String getSibatte() {
        return getDokata("sibatte");
    }

    public static String getHonkide() {
        return getDokata("honkide");
    }

    public static String getHentaiDaisuki() {
        return getDokata("hentaidaisuki");
    }

    public static String getFuroShaNoOssan() {
        return getDokata("furoshanoossan");
    }

    public static String getYagaiDe2() {
        return getDokata("yagaide2");
    }

    public static String getMakkuro() {
        return getDokata("makkuro");
    }

    public static String getYarimakuri() {
        return getDokata("yarimakuri");
    }

    public static String getKushizashi() {
        return getDokata("kushizashi");
    }

    public static String getRoshutsuShitaZe() {
        return getDokata("roshutsushitaze");
    }

    public static String getYarouze2() {
        return getDokata("yarouze2");
    }

    public static String getIssyoni() {
        return getDokata("issyoni");
    }

    public static String getOkayamade() {
        return getDokata("okayamade");
    }

    public static String getKurashikiMukaiyamaRoshutsuZuki() {
        return getDokata("kurashikimukaiyamaroshutsuzuki");
    }

    public static enum Dokata {
        KUSOMAMIRE(FNDokataUtil::getKusomamire),
        YATTAZE(FNDokataUtil::getYattaze),
        YARITAI(FNDokataUtil::getYaritai),
        SENZURI(FNDokataUtil::getSenzuri),
        KUSOKUSOKUSO(FNDokataUtil::getKusoKusoKuso),
        KUSOMAMIREDE(FNDokataUtil::getKusomamirede),
        KUSOZUKIOYAJIJISAMABOSHU(FNDokataUtil::getKusozukiOyajiJisamaBoshu),
        KUSOMAMIREDE2(FNDokataUtil::getKusomamirede2),
        HAYAKUYARIMAKURITAIYO(FNDokataUtil::getHayakuYariMakuriTaiyo),
        HAYAKUKUSOMAMIRENI(FNDokataUtil::getHayakuKusoMamireni),
        KUSOMAMIRE2(FNDokataUtil::getKusomamire2),
        KUSOMAMIRE3(FNDokataUtil::getKusomamire3),
        KINDANSYOUJOGA(FNDokataUtil::getKindanSyouJoga),
        KUSOOYAJI(FNDokataUtil::getKusoOyaji),
        YAROUZE(FNDokataUtil::getYarouze),
        YAGAIDE(FNDokataUtil::getYagaiDe),
        KUSOMAMIRE4(FNDokataUtil::getKusomamire4),
        YARITEXE(FNDokataUtil::getYaritexe),
        KUSOMAMIREDE3(FNDokataUtil::getKusomamirede3),
        KUSOMAMIRE5(FNDokataUtil::getKusomamire5),
        KUSO(FNDokataUtil::getKuso),
        KUSODAISUKI(FNDokataUtil::getKusoDaisuki),
        YAMANONAKADE(FNDokataUtil::getYamaNoNakaDe),
        YAMANONAKADE2(FNDokataUtil::getYamaNoNakaDe2),
        YAMANONAKADE3(FNDokataUtil::getYamaNoNakaDe3),
        SIBATTE(FNDokataUtil::getSibatte),
        HONKIDE(FNDokataUtil::getHonkide),
        HENTAIDAISUKI(FNDokataUtil::getHentaiDaisuki),
        FUROSHANOOSSAN(FNDokataUtil::getFuroShaNoOssan),
        YAGAIDE2(FNDokataUtil::getYagaiDe2),
        MAKKURO(FNDokataUtil::getMakkuro),
        YARIMAKURI(FNDokataUtil::getYarimakuri),
        KUSHIZASHI(FNDokataUtil::getKushizashi),
        ROSHUTSUSHITAZE(FNDokataUtil::getRoshutsuShitaZe),
        YAROUZE2(FNDokataUtil::getYarouze2),
        ISSYONI(FNDokataUtil::getIssyoni),
        OKAYAMADE(FNDokataUtil::getOkayamade),
        KURASHIKIMUKAIYAMAROSHUTSUZUKI(FNDokataUtil::getKurashikiMukaiyamaRoshutsuZuki);
        private final Supplier<String> str;

        Dokata(Supplier<String> st) {
            this.str = st;
        }

        public String getString() {
            return str.get();
        }
    }
}
