package com.example.ui.util

object Strings {
    fun get(key: String, lang: String): String {
        val isBn = lang.equals("bn", ignoreCase = true)
        return when (key) {
            // Navigation
            "nav_home" -> if (isBn) "হোম" else "Home"
            "nav_plan" -> if (isBn) "প্ল্যান" else "Plan"
            "nav_focus" -> if (isBn) "ফোকাস" else "Focus"
            "nav_journal" -> if (isBn) "জার্নাল" else "Journal"
            "nav_progress" -> if (isBn) "অগ্রগতি" else "Progress"
            "study_plan_title" -> if (isBn) "স্টাডি প্ল্যান" else "Study Plan"

            // Header & App Info
            "app_name" -> if (isBn) "আইইএলটিএস জার্নি" else "IELTS Journey"
            "app_tagline" -> if (isBn) "পরিকল্পনা করুন। অগ্রগতি ট্র্যাক করুন। লক্ষ্য অর্জন করুন।" else "Plan your study. Track your progress. Achieve your target."
            "tagline_short" -> if (isBn) "৬ সপ্তাহের প্রস্তুতি সঙ্গী" else "6-Week IELTS Master Plan"

            // Home / Dashboard
            "today_plan" -> if (isBn) "আজকের স্টাডি প্ল্যান" else "Today's Study Plan"
            "overall_progress" -> if (isBn) "সামগ্রিক অগ্রগতি" else "Overall Progress"
            "quick_actions" -> if (isBn) "দ্রুত অ্যাকশন" else "Quick Actions"
            "study_streak" -> if (isBn) "স্টাডি স্ট্রিক" else "Study Streak"
            "days_active" -> if (isBn) "দিন সক্রিয়" else "Days Active"
            "tasks_done" -> if (isBn) "টাস্ক সম্পন্ন" else "Tasks Done"
            "study_time_today" -> if (isBn) "আজকের পড়ার সময়" else "Study Time Today"
            "daily_goal" -> if (isBn) "দৈনিক লক্ষ্য" else "Daily Goal"
            "continue_plan" -> if (isBn) "প্ল্যান চালিয়ে যান" else "Continue Plan"
            "start_focus" -> if (isBn) "টাইমার চালু" else "Start Timer"
            "add_journal" -> if (isBn) "নোট লিখুন" else "Add Journal"
            "record_test" -> if (isBn) "টেস্ট স্কোর" else "Practice Test"
            "view_errors" -> if (isBn) "ভুল বিশ্লেষণ" else "Error Log"
            "view_vocab" -> if (isBn) "শব্দভান্ডার" else "Vocabulary"
            "view_assessments" -> if (isBn) "স্ব-মূল্যায়ন" else "Assessments"
            "resources" -> if (isBn) "রিসোর্স ও গাইড" else "Resources"
            "settings" -> if (isBn) "সেটিংস" else "Settings"

            // Study Plan
            "all_phases" -> if (isBn) "সকল ফেজ" else "All Phases"
            "filter_all" -> if (isBn) "সব" else "All"
            "filter_incomplete" -> if (isBn) "বাকি আছে" else "Incomplete"
            "filter_completed" -> if (isBn) "সম্পন্ন" else "Completed"
            "filter_today" -> if (isBn) "আজকের" else "Today"
            "search_tasks" -> if (isBn) "টাস্ক খুঁজুন..." else "Search tasks..."
            "examiner_tip" -> if (isBn) "পরীক্ষকের টিপ" else "Examiner Tip"
            "common_mistake" -> if (isBn) "সাধারণ ভুল" else "Common Mistake"
            "reflection_questions" -> if (isBn) "চিন্তাভাবনার প্রশ্ন" else "Reflection Questions"
            "day" -> if (isBn) "দিন" else "Day"
            "phase" -> if (isBn) "ফেজ" else "Phase"
            "week" -> if (isBn) "সপ্তাহ" else "Week"
            "min" -> if (isBn) "মিনিট" else "min"
            "hours" -> if (isBn) "ঘণ্টা" else "hours"

            // Focus Timer
            "focus_timer" -> if (isBn) "ফোকাস টাইমার" else "Focus Timer"
            "pomodoro" -> if (isBn) "পোমোডোরো (২৫ মি.)" else "Pomodoro (25m)"
            "short_break" -> if (isBn) "ছোট বিরতি (৫ মি.)" else "Short Break (5m)"
            "long_break" -> if (isBn) "বড় বিরতি (১৫ মি.)" else "Long Break (15m)"
            "custom" -> if (isBn) "কাস্টম" else "Custom"
            "start" -> if (isBn) "শুরু" else "Start"
            "pause" -> if (isBn) "বিরতি" else "Pause"
            "resume" -> if (isBn) "চালিয়ে যান" else "Resume"
            "reset" -> if (isBn) "রিসেট" else "Reset"
            "skip" -> if (isBn) "স্কিপ" else "Skip"
            "select_topic" -> if (isBn) "বিষয় নির্বাচন করুন" else "Select Topic"
            "sessions_today" -> if (isBn) "আজকের সম্পন্ন সেশন" else "Sessions Today"
            "session_completed_title" -> if (isBn) "ফোকাস সেশন সম্পন্ন!" else "Focus Session Finished!"
            "session_completed_prompt" -> if (isBn) "এই সেশনে আপনি কি কি শিখলেন বা শেষ করলেন?" else "What did you accomplish during this session?"
            "save_session" -> if (isBn) "সংরক্ষণ করুন" else "Save Session"

            // Journal
            "study_journal" -> if (isBn) "স্টাডি জার্নাল" else "Study Journal"
            "new_entry" -> if (isBn) "নতুন এন্ট্রি" else "New Entry"
            "edit_entry" -> if (isBn) "এন্ট্রি সম্পাদনা" else "Edit Entry"
            "search_journal" -> if (isBn) "জার্নাল খুঁজুন..." else "Search journal..."
            "what_studied" -> if (isBn) "আজকে কি পড়লেন?" else "What I studied"
            "what_learned" -> if (isBn) "কি কি শিখলেন?" else "What I learned"
            "mistakes_made" -> if (isBn) "যে ভুলগুলো হয়েছে" else "Mistakes I made"
            "improvement_plan" -> if (isBn) "উন্নতির পরিকল্পনা" else "What I need to improve"
            "duration_mins" -> if (isBn) "পড়ার সময় (মিনিট)" else "Duration (mins)"
            "questions_attempted" -> if (isBn) "প্রশ্ন সমাধান" else "Questions attempted"
            "questions_correct" -> if (isBn) "সঠিক উত্তর" else "Correct answers"
            "save" -> if (isBn) "সংরক্ষণ" else "Save"
            "delete" -> if (isBn) "মুছুন" else "Delete"
            "cancel" -> if (isBn) "বাতিল" else "Cancel"
            "confirm_delete" -> if (isBn) "আপনি কি নিশ্চিত মুছে ফেলতে চান?" else "Are you sure you want to delete this?"
            "empty_journal" -> if (isBn) "এখনও কোনো জার্নাল এন্ট্রি নেই। 'নতুন এন্ট্রি' চাপুন।" else "No journal entries yet. Tap '+ New Entry' to log your learning."

            // Progress
            "progress_analytics" -> if (isBn) "অগ্রগতি ও বিশ্লেষণ" else "Progress Analytics"
            "target_vs_current" -> if (isBn) "টার্গেট বনাম বর্তমান" else "Target vs Current"
            "phase_breakdown" -> if (isBn) "ফেজ অনুযায়ী অগ্রগতি" else "Phase Breakdown"
            "skill_progress" -> if (isBn) "স্কিল অনুযায়ী অগ্রগতি" else "Skill Breakdown"
            "reading" -> if (isBn) "রিডিং" else "Reading"
            "listening" -> if (isBn) "লিসেনিং" else "Listening"
            "writing" -> if (isBn) "রাইটিং" else "Writing"
            "speaking" -> if (isBn) "স্পিকিং" else "Speaking"
            "practice_tests_history" -> if (isBn) "প্র্যাকটিস টেস্টের রেকর্ড" else "Practice Test History"
            "error_categories" -> if (isBn) "ভুলের ধরণসমূহ" else "Frequent Mistake Categories"

            // Practice Test Tracker
            "practice_tests" -> if (isBn) "প্র্যাকটিস টেস্ট ট্র্যাকার" else "Practice Tests Tracker"
            "add_test" -> if (isBn) "টেস্ট যোগ করুন" else "Log Practice Test"
            "raw_score" -> if (isBn) "সঠিক উত্তর (স্কোর)" else "Raw Score"
            "estimated_band" -> if (isBn) "আনুমানিক ব্যান্ড" else "Estimated Band"
            "accuracy" -> if (isBn) "নির্ভুলতা" else "Accuracy"
            "historical_ref" -> if (isBn) "রেফারেন্স রেকর্ড" else "Historical Reference"

            // Error Log
            "error_log" -> if (isBn) "ভুল বিশ্লেষণ ও পর্যালোচনা" else "Error Log & Improvement"
            "add_error" -> if (isBn) "ভুল এন্ট্রি করুন" else "Log Error"
            "question_type" -> if (isBn) "প্রশ্নের ধরণ" else "Question Type"
            "my_answer" -> if (isBn) "আমার ভুল উত্তর" else "My Answer"
            "correct_answer" -> if (isBn) "সঠিক উত্তর" else "Correct Answer"
            "why_wrong" -> if (isBn) "কেন ভুল হয়েছিল" else "Why Answer Was Wrong"
            "strategy_avoid" -> if (isBn) "ভুল এড়ানোর কৌশল" else "Strategy to Avoid"
            "mistake_category" -> if (isBn) "ভুলের ক্যাটাগরি" else "Mistake Category"

            // Vocabulary
            "vocab_notebook" -> if (isBn) "শব্দভান্ডার ও প্রতিশব্দ" else "Vocabulary & Synonyms"
            "add_vocab" -> if (isBn) "নতুন শব্দ যোগ" else "Add Word"
            "search_vocab" -> if (isBn) "শব্দ খুঁজুন..." else "Search word or synonym..."
            "synonyms" -> if (isBn) "প্রতিশব্দ" else "Synonyms"
            "mastery_learning" -> if (isBn) "শিখছি" else "Learning"
            "mastery_familiar" -> if (isBn) "পরিচিত" else "Familiar"
            "mastery_mastered" -> if (isBn) "সম্পূর্ণ জানা" else "Mastered"

            // Assessments
            "weekly_assessments" -> if (isBn) "সাপ্তাহিক মূল্যায়ন" else "Weekly Self-Assessments"
            "readiness_check" -> if (isBn) "প্রস্তুতি যাচাই" else "Readiness Check"
            "action_plan" -> if (isBn) "অ্যাকশন প্ল্যান" else "Action Plan"

            // Settings
            "settings_title" -> if (isBn) "সেটিংস ও ডেটা" else "Settings & Backup"
            "interface_language" -> if (isBn) "ভাষার পছন্দ" else "Interface Language"
            "target_scores" -> if (isBn) "টার্গেট স্কোরসমূহ" else "Target Scores"
            "start_date" -> if (isBn) "শুরুর তারিখ" else "Study Plan Start Date"
            "test_date" -> if (isBn) "পরীক্ষার তারিখ" else "Target Test Date"
            "backup_export" -> if (isBn) "ডেটা এক্সপোর্ট (JSON)" else "Export Backup (JSON)"
            "backup_import" -> if (isBn) "ডেটা ইমপোর্ট (JSON)" else "Import Backup (JSON)"
            "danger_zone" -> if (isBn) "রিসেট অপশন" else "Reset Options"
            "reset_tasks_title" -> if (isBn) "সকল টাস্ক রিসেট করুন" else "Reset All Tasks (Uncheck All)"
            "reset_tasks_desc" -> if (isBn) "সব চেকমার্ক আনচেক হবে, কিন্তু আপনার জার্নাল ও টেস্ট রেকর্ড অক্ষত থাকবে।" else "Unchecks all tasks. Keeps your journal, test records, and notes safe."
            "reset_all_data" -> if (isBn) "সম্পূর্ণ অ্যাপ রিসেট" else "Reset Entire Application Data"
            "reset_confirmed" -> if (isBn) "সফলভাবে রিসেট হয়েছে" else "Successfully reset"

            else -> key
        }
    }
}
