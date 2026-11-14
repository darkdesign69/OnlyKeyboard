package dev.darkdesign69.onlykeyboard.kiebord.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

/** Current app language ("en" or "bn"), provided from MainActivity. */
val LocalAppLanguage = compositionLocalOf { "en" }

/** Returns the Bangla text for [en] when the app language is Bangla, otherwise [en]. */
@Composable
fun tr(en: String): String {
    return if (LocalAppLanguage.current == "bn") (BanglaStrings[en] ?: en) else en
}

private val BanglaStrings: Map<String, String> = mapOf(
    // Main menu
    "Quick Settings" to "দ্রুত সেটিংস",
    "Preferences" to "পছন্দসমূহ",
    "Appearance & Layouts" to "চেহারা ও লেআউট",
    "Text correction" to "টেক্সট সংশোধন",
    "More Languages" to "আরও ভাষা",
    "Install Arabic, Chakma, Syloti language" to "আরবি, চাকমা, সিলেটি ভাষা ইনস্টল করুন",
    "Advanced" to "উন্নত",
    "Gif Quality" to "জিআইএফ কোয়ালিটি",
    "Release notes" to "রিলিজ নোট",
    "App Language" to "অ্যাপের ভাষা",
    "Change between Bangla and English" to "বাংলা ও ইংরেজির মধ্যে পরিবর্তন করুন",
    "Appearance & Themes" to "চেহারা ও থিম",
    "Keyboard Layouts" to "কিবোর্ড লেআউট",
    "Language Extensions" to "ভাষা এক্সটেনশন",
    "Corrections & Suggestions" to "সংশোধন ও পরামর্শ",
    "Smart Gestures" to "স্মার্ট জেসচার",
    "Voice Typing" to "ভয়েস টাইপিং",
    "Clipboard" to "ক্লিপবোর্ড",
    "Personal Dictionary" to "ব্যক্তিগত অভিধান",
    "Backup & Restore" to "ব্যাকআপ ও রিস্টোর",
    "About Only Keyboard" to "Only Keyboard সম্পর্কে",
    // Preferences
    "Auto-capitalization" to "স্বয়ংক্রিয় বড় হাতের অক্ষর",
    "Capitalize the first word of each sentence" to "প্রতিটি বাক্যের প্রথম শব্দ বড় হাতের হবে",
    "Double-space period" to "ডাবল-স্পেস দাঁড়ি",
    "Double tap on spacebar inserts a period followed by a space" to "স্পেসবারে দুইবার ট্যাপ করলে দাঁড়ির পর স্পেস বসবে",
    "Double-space tab" to "ডাবল-স্পেস ট্যাব",
    "Double tap on spacebar inserts a tab" to "স্পেসবারে দুইবার ট্যাপ করলে ট্যাব বসবে",
    "Clipboard Recent Items" to "ক্লিপবোর্ডের সাম্প্রতিক আইটেম",
    "Show recent copied or cut text in clipboard" to "ক্লিপবোর্ডে সাম্প্রতিক কপি/কাট করা টেক্সট দেখান",
    "Keep copied text on Clipboard" to "কপি করা টেক্সট ক্লিপবোর্ডে রাখুন",
    "Time before automatically delete unpinned items" to "পিন না করা আইটেম স্বয়ংক্রিয়ভাবে মোছার আগের সময়",
    "Show copied images on Clipboard" to "কপি করা ছবি ক্লিপবোর্ডে দেখান",
    "Show screenshots or copied images on Clipboard" to "স্ক্রিনশট বা কপি করা ছবি ক্লিপবোর্ডে দেখান",
    "Vibrate on keypress" to "কী চাপলে ভাইব্রেট",
    "Sound on keypress" to "কী চাপলে শব্দ",
    "Popup on keypress" to "কী চাপলে পপআপ",
    "Voice input key" to "ভয়েস ইনপুট কী",
    "Show Emoji Key" to "ইমোজি কী দেখান",
    "Switch to Emoji button" to "ইমোজি বাটনে যান",
    "Show Globe Key" to "গ্লোব কী দেখান",
    "Switch keyboard language" to "কিবোর্ডের ভাষা পরিবর্তন করুন",
    "Allow Other Keyboards" to "অন্যান্য কিবোর্ড অনুমতি দিন",
    "Globe key switches to other keyboards also" to "গ্লোব কী অন্য কিবোর্ডেও পরিবর্তন করবে",
    "Volume cursor" to "ভলিউম কার্সর",
    "Use the volume keys to move the cursor" to "কার্সর সরাতে ভলিউম কী ব্যবহার করুন",
    "Smart volume key cursor control" to "স্মার্ট ভলিউম কী কার্সর নিয়ন্ত্রণ",
    "Disable cursor movement using volume keys during audio playback" to "অডিও চলার সময় ভলিউম কী দিয়ে কার্সর সরানো বন্ধ থাকবে",
    "Off" to "বন্ধ",
    "1 hour" to "১ ঘণ্টা",
    "6 hours" to "৬ ঘণ্টা",
    "12 hours" to "১২ ঘণ্টা",
    "24 hours" to "২৪ ঘণ্টা",
    "3 days" to "৩ দিন",
    "7 days" to "৭ দিন",
    // Appearance & layouts
    "Enable number row" to "নম্বর সারি চালু করুন",
    "Adds an extra row" to "একটি অতিরিক্ত সারি যোগ করে",
    "Large number row" to "বড় নম্বর সারি",
    "Hide long press hints" to "লং প্রেস ইঙ্গিত লুকান",
    "Hide small labels from key corners for a cleaner view" to "পরিচ্ছন্ন চেহারার জন্য কী-এর কোণা থেকে ছোট লেবেল লুকান",
    "Enable keyboard resizing" to "কিবোর্ড রিসাইজ চালু করুন",
    "Keyboard Height (Portrait)" to "কিবোর্ডের উচ্চতা (পোর্ট্রেট)",
    "Keyboard Height (Landscape)" to "কিবোর্ডের উচ্চতা (ল্যান্ডস্কেপ)",
    "One Handed Keyboard Width (Portrait)" to "এক হাতের কিবোর্ডের প্রস্থ (পোর্ট্রেট)",
    "One Handed Keyboard Width (Landscape)" to "এক হাতের কিবোর্ডের প্রস্থ (ল্যান্ডস্কেপ)",
    "Enable split keyboard" to "স্প্লিট কিবোর্ড চালু করুন",
    "Splits the keys in two halves in landscape" to "ল্যান্ডস্কেপে কী দুই ভাগে ভাগ হবে",
    "Enable split keyboard for foldable phone" to "ফোল্ডেবল ফোনের জন্য স্প্লিট কিবোর্ড",
    "Splits the keys when the screen is wide (unfolded)" to "স্ক্রিন চওড়া (খোলা) থাকলে কী ভাগ হবে",
    "Forced enter button" to "বাধ্যতামূলক এন্টার বাটন",
    "Do not show emoji on enter button" to "এন্টার বাটনে ইমোজি দেখাবেন না",
    // Text correction
    "Block offensive words" to "আপত্তিকর শব্দ ব্লক করুন",
    "Do not suggest potentially offensive words" to "আপত্তিকর হতে পারে এমন শব্দ প্রস্তাব করবেন না",
    "Auto-correction" to "স্বয়ংক্রিয় সংশোধন",
    "Spacebar and punctuation automatically correct mistyped words" to "স্পেসবার ও যতিচিহ্ন ভুল টাইপ করা শব্দ স্বয়ংক্রিয়ভাবে ঠিক করে",
    "Phonetic auto-correction" to "ফোনেটিক স্বয়ংক্রিয় সংশোধন",
    "Automatically correct mistyped Phonetic words in input field" to "ইনপুট ফিল্ডে ভুল ফোনেটিক শব্দ স্বয়ংক্রিয়ভাবে ঠিক করে",
    "Show suggestions" to "পরামর্শ দেখান",
    "Display suggested words while typing" to "টাইপ করার সময় প্রস্তাবিত শব্দ দেখান",
    "Personalized suggestions" to "ব্যক্তিগতকৃত পরামর্শ",
    "Learn from your communications and typed data to improve suggestions" to "পরামর্শ উন্নত করতে আপনার টাইপ করা ডেটা থেকে শিখবে",
    "Next-word suggestions" to "পরবর্তী শব্দের পরামর্শ",
    "Use the previous word in making suggestions" to "পরামর্শ দিতে আগের শব্দ ব্যবহার করুন",
    // Advanced
    "Key popup dismiss delay" to "কী পপআপ বন্ধের বিলম্ব",
    "Keypress vibration duration" to "কী চাপার ভাইব্রেশনের সময়",
    "Keypress sound volume" to "কী চাপার শব্দের ভলিউম",
    "Key long press delay" to "কী লং প্রেস বিলম্ব",
    "Space cursor long press delay" to "স্পেস কার্সর লং প্রেস বিলম্ব",
    "Space cursor speed" to "স্পেস কার্সরের গতি",
    "Emoji for physical keyboard" to "ফিজিক্যাল কিবোর্ডের জন্য ইমোজি",
    "Physical Alt key shows the emoji palette" to "ফিজিক্যাল Alt কী ইমোজি প্যালেট দেখায়",
    "Show typed word" to "টাইপ করা শব্দ দেখান",
    "Show typed word as the first suggestion in phonetic mode" to "ফোনেটিক মোডে টাইপ করা শব্দ প্রথম পরামর্শ হিসেবে দেখান",
    "Voice typing engine" to "ভয়েস টাইপিং ইঞ্জিন",
    "Default" to "ডিফল্ট",
    "System default" to "সিস্টেম ডিফল্ট",
    // GIF
    "Low" to "কম",
    "Medium" to "মাঝারি",
    "High" to "উচ্চ",
    "Smaller files, faster sending" to "ছোট ফাইল, দ্রুত পাঠানো",
    "Balanced size and quality" to "আকার ও মানের ভারসাম্য",
    "Best quality, larger files" to "সেরা মান, বড় ফাইল",
    // App language
    "English" to "ইংরেজি",
    "Bangla" to "বাংলা",
    // Release notes
    "What's new" to "নতুন কী আছে",
    // Titles
    "Typing Preferences" to "টাইপিং পছন্দসমূহ",
    "Advanced settings" to "উন্নত সেটিংস"
)
