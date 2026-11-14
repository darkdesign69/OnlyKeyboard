# The Theme Designer is discovered reflectively by ThemeDesignerRegistry (core:theme).
# Keep the implementation object and its singleton field so R8 does not strip/rename it.
-keep class dev.darkdesign69.onlykeyboard.feature.themedesigner.ThemeDesignerFeatureImpl {
    public static ** INSTANCE;
}

