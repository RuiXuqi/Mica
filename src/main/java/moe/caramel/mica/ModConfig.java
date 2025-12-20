package moe.caramel.mica;

import moe.caramel.mica.natives.DwmApi;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.jspecify.annotations.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;

public class ModConfig {
    private static Configuration config;

    public static final String[] CATEGORIES = new String[]{"general", "border", "caption", "title_text"};

    // General
    public static boolean useImmersiveDarkMode = false;
    public static DwmApi.DWM_SYSTEMBACKDROP_TYPE systemBackdropType = DwmApi.DWM_SYSTEMBACKDROP_TYPE.DWMSBT_AUTO;
    public static DwmApi.DWM_WINDOW_CORNER_PREFERENCE windowCorner = DwmApi.DWM_WINDOW_CORNER_PREFERENCE.DWMWCP_DEFAULT;

    // Border
    public static boolean useDefaultBorder = true;
    public static boolean hideWindowBorder = false;
    public static int borderColor = DwmApi.DWMWA_COLOR_DEFAULT;

    // Caption
    public static boolean useDefaultCaption = true;
    public static int captionColor = DwmApi.DWMWA_COLOR_DEFAULT;

    // Title Text
    public static boolean useDefaultText = true;
    public static int textColor = DwmApi.DWMWA_COLOR_DEFAULT;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            config.load();
        }
        syncConfig();
    }

    public static void syncConfig() {
        final String PREFIX = "mica.category.";
        String entryPrefix;

        // General
        entryPrefix = PREFIX + CATEGORIES[0];
        config.getCategory(CATEGORIES[0]).setLanguageKey(entryPrefix);
        entryPrefix += ".";

        useImmersiveDarkMode = config.get(
                CATEGORIES[0],
                "useImmersiveDarkMode",
                false,
                "Can enable \"Immersive Dark Mode\" by enabling the setting."
        ).setLanguageKey(entryPrefix + "use_immersive_dark_mode").getBoolean();

        systemBackdropType = getEnum(
                CATEGORIES[0],
                "systemBackdropType",
                DwmApi.DWM_SYSTEMBACKDROP_TYPE.DWMSBT_AUTO,
                "Can change it to a system backdrop material that is available in Windows 11."
                        + "\n(Supported starting with Windows 11 build 22621.)",
                entryPrefix + "system_backdrop_type"
        );

        windowCorner = getEnum(
                CATEGORIES[0],
                "windowCornerPreference",
                DwmApi.DWM_WINDOW_CORNER_PREFERENCE.DWMWCP_DEFAULT,
                "Can change the corner curvature of the Window.",
                entryPrefix + "corner_preference"
        );

        // Border
        entryPrefix = PREFIX + CATEGORIES[1];
        config.getCategory(CATEGORIES[1]).setLanguageKey(entryPrefix);
        entryPrefix += ".";

        useDefaultBorder = config.get(
                CATEGORIES[1],
                "useDefaultBorderColor",
                true,
                "Can customize the window border by turning off this setting."
        ).setLanguageKey(entryPrefix + "use_default_border").getBoolean();

        hideWindowBorder = config.get(
                CATEGORIES[1],
                "hideWindowBorder",
                false,
                "Can enable setting to remove the border of the window."
        ).setLanguageKey(entryPrefix + "hide_border").getBoolean();

        borderColor = config.get(
                CATEGORIES[1],
                "borderColor",
                DwmApi.DWMWA_COLOR_DEFAULT,
                "Can change the border color of the window."
        ).setLanguageKey(entryPrefix + "border_color").getInt();

        // Caption
        entryPrefix = PREFIX + CATEGORIES[2];
        config.getCategory(CATEGORIES[2]).setLanguageKey(entryPrefix);
        entryPrefix += ".";

        useDefaultCaption = config.get(
                CATEGORIES[2],
                "useDefaultCaptionColor",
                true,
                "Can customize the title bar color by turning off this setting."
        ).setLanguageKey(entryPrefix + "use_default_caption").getBoolean();

        captionColor = config.get(
                CATEGORIES[2],
                "captionColor",
                DwmApi.DWMWA_COLOR_DEFAULT,
                "Can change the color of the title bar."
        ).setLanguageKey(entryPrefix + "caption_color").getInt();

        // Title Text
        entryPrefix = PREFIX + CATEGORIES[3];
        config.getCategory(CATEGORIES[3]).setLanguageKey(entryPrefix);
        entryPrefix += ".";

        useDefaultText = config.get(
                CATEGORIES[3],
                "useDefaultTextColor",
                true,
                "Can customize the title text color by turning off this setting."
        ).setLanguageKey(entryPrefix + "use_default_color").getBoolean();

        textColor = config.get(
                CATEGORIES[3],
                "textColor",
                DwmApi.DWMWA_COLOR_DEFAULT,
                "Can change the color of the title text."
        ).setLanguageKey(entryPrefix + "text_color").getInt();

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    private static <E extends Enum<E>> @NonNull E getEnum(String category, String key, @NonNull E defaultEnum, String comment, @Nullable String langKey) {
        Class<E> enumClass = defaultEnum.getDeclaringClass();
        String[] values = Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        String[] names = values;
        if (langKey != null && ITranslatable.class.isAssignableFrom(enumClass)) {
            names = Arrays.stream(enumClass.getEnumConstants()).map(e -> langKey + ".type." + ((ITranslatable) e).getLangKey()).toArray(String[]::new);
        }
        Property prop = config.get(category, key, defaultEnum.name(), comment, values, names);
        if (langKey != null) prop.setLanguageKey(langKey);
        validifyStringProp(prop);
        try {
            return Enum.valueOf(enumClass, prop.getString());
        } catch (IllegalArgumentException e) {
            prop.set(defaultEnum.name());
            return defaultEnum;
        }
    }

    private static void validifyStringProp(@Nonnull Property prop) {
        String[] validValues = prop.getValidValues();
        if (validValues == null || validValues.length == 0) return;

        String currentValue = prop.getString();
        for (String valid : validValues) {
            if (valid.equalsIgnoreCase(currentValue)) {
                if (!valid.equals(currentValue)) prop.set(valid); // Correct case
                return;
            }
        }

        prop.set(prop.getDefault());
    }

    public interface ITranslatable {
        String getLangKey();
    }
}
