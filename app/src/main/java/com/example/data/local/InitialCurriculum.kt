package com.example.data.local

import com.example.data.local.entity.*

object InitialCurriculum {

    fun getDefaultPreferences(): UserPreferencesEntity {
        return UserPreferencesEntity(
            id = 1,
            language = "en",
            themeMode = "system",
            ieltsType = "Academic",
            targetBandOverall = 7.0,
            targetBandListening = 7.5,
            targetBandReading = 7.0,
            targetBandWriting = 6.5,
            targetBandSpeaking = 7.0,
            studyStartDate = "",
            targetTestDate = "",
            dailyGoalMinutes = 150,
            focusSessionMinutes = 25,
            shortBreakMinutes = 5,
            longBreakMinutes = 15,
            longBreakInterval = 4,
            remindersEnabled = true,
            isOnboarded = false
        )
    }

    val phases = listOf(
        StudyPhaseEntity(
            id = 1,
            phaseNumber = 1,
            title = "Phase 1: Foundations & Test Overview",
            dayStart = 1,
            dayEnd = 6,
            goal = "Understand format, requirements, and scoring of each IELTS component",
            description = "Covers logistics, scoring criteria, and in-depth fundamental overviews of Speaking, Listening, Reading, and Writing."
        ),
        StudyPhaseEntity(
            id = 2,
            phaseNumber = 2,
            title = "Phase 2: Reading Mastery",
            dayStart = 7,
            dayEnd = 13,
            goal = "Master strategies and practice with authentic materials",
            description = "Question-type techniques, skimming & scanning, keywords & paraphrasing, error analysis, and strict timed practice."
        ),
        StudyPhaseEntity(
            id = 3,
            phaseNumber = 3,
            title = "Phase 3: Writing Task 2",
            dayStart = 14,
            dayEnd = 20,
            goal = "Master Task 2 structure, approach, and assessment criteria",
            description = "Essay structures (Opinion, Discussion, Problem-Solution, etc.), brainstorming (5 Questions, Mind Mapping), body paragraph development, editing & timed essays."
        ),
        StudyPhaseEntity(
            id = 4,
            phaseNumber = 4,
            title = "Phase 4: Speaking",
            dayStart = 21,
            dayEnd = 27,
            goal = "Develop IELTS Speaking skills across all three parts",
            description = "4 Assessment criteria, Part 2 PREP long-turn strategy, Part 3 question types, recordings & analysis, mock speaking test, targeted fluency and pronunciation improvements."
        ),
        StudyPhaseEntity(
            id = 5,
            phaseNumber = 5,
            title = "Phase 5: Writing Task 1",
            dayStart = 28,
            dayEnd = 34,
            goal = "Master Task 1 for your specific test type",
            description = "Graphs, charts, tables, processes, maps; 4-paragraph structure, trend vocabulary, comparisons, untimed & timed practice."
        ),
        StudyPhaseEntity(
            id = 6,
            phaseNumber = 6,
            title = "Phase 6: Listening & Final Prep",
            dayStart = 35,
            dayEnd = 41,
            goal = "Master Listening strategies and achieve final test readiness",
            description = "10+ Question types, prediction & note-taking symbols, full listening tests, answer transfer practice, error traps, packing checklist & test day mindset."
        )
    )

    val days = listOf(
        // Phase 1: Days 1-6
        StudyDayEntity(
            id = 1,
            dayNumber = 1,
            phaseId = 1,
            weekNumber = 1,
            title = "Test Overview & Logistics",
            learningObjective = "Determine test requirements, set target band scores, and understand IELTS structure.",
            recommendedDurationMinutes = 120,
            examinerTip = "Understanding the scoring criteria is just as important as improving your English skills. Many candidates with excellent English still score poorly because they don't understand what examiners look for.",
            commonMistake = "Beginning studying without clear score targets, wasting time on skills that won't impact their required band score.",
            reflectionQuestions = "Which section do you think will be most challenging based on your current English level?\nWhat score improvements will make the biggest difference to your overall band?"
        ),
        StudyDayEntity(
            id = 2,
            dayNumber = 2,
            phaseId = 1,
            weekNumber = 1,
            title = "Speaking Overview",
            learningObjective = "Learn Speaking test format and master the four assessment criteria.",
            recommendedDurationMinutes = 120,
            examinerTip = "In your first few days, focus on understanding the test rather than improving your English. A clear understanding of requirements will make your future practice much more effective.",
            commonMistake = "Jumping straight into practice tests without understanding what each section is testing.",
            reflectionQuestions = "What aspects of the speaking test surprised you?\nWhich speaking assessment criteria do you think will be most challenging for you?"
        ),
        StudyDayEntity(
            id = 3,
            dayNumber = 3,
            phaseId = 1,
            weekNumber = 1,
            title = "Listening Overview",
            learningObjective = "Understand all listening question types, section characteristics, and scoring.",
            recommendedDurationMinutes = 120,
            examinerTip = "The listening test assesses your ability to understand main ideas, specific factual information, opinions, attitudes, and purposes. Make sure you understand these different skills.",
            commonMistake = "Focusing only on understanding accents rather than developing specific listening skills tested in IELTS.",
            reflectionQuestions = "Which listening question types seem most difficult to you?\nWhat strategies for predicting answers before listening sound most helpful?"
        ),
        StudyDayEntity(
            id = 4,
            dayNumber = 4,
            phaseId = 1,
            weekNumber = 1,
            title = "Reading Overview",
            learningObjective = "Master skimming and scanning techniques and 60-minute time management.",
            recommendedDurationMinutes = 120,
            examinerTip = "Many students try to read the entire passage first, but this often leads to time pressure. Learn skimming and scanning techniques to locate information efficiently.",
            commonMistake = "Spending too much time on difficult questions, which doesn't leave enough time for questions you might find easier.",
            reflectionQuestions = "How does your current reading approach differ from the recommended strategies?\nWhich reading question types do you anticipate being most challenging?"
        ),
        StudyDayEntity(
            id = 5,
            dayNumber = 5,
            phaseId = 1,
            weekNumber = 1,
            title = "Writing Task 1 Overview",
            learningObjective = "Understand Academic vs General Training Task 1 and learn trend vocabulary.",
            recommendedDurationMinutes = 120,
            examinerTip = "For Academic Task 1, you're not expected to interpret data or give your opinion. Your task is to accurately report the key features of the information provided.",
            commonMistake = "Writing too much about minor details rather than focusing on significant trends or features.",
            reflectionQuestions = "What aspects of Writing Task 1 seem most challenging to you?\nDo you feel more comfortable describing trends (Academic) or writing letters (General Training)?"
        ),
        StudyDayEntity(
            id = 6,
            dayNumber = 6,
            phaseId = 1,
            weekNumber = 1,
            title = "Writing Task 2 Overview & Self-Assessment",
            learningObjective = "Understand Task 2 essay types and complete the Week 1 Self-Assessment.",
            recommendedDurationMinutes = 150,
            examinerTip = "Task 2 carries twice the weight of Task 1 in the Writing score. If short on time, prioritize Task 2 practice.",
            commonMistake = "Not fully analyzing the question prompt, leading to off-topic responses that severely limit your score.",
            reflectionQuestions = "Which essay types (opinion, discussion, problem-solution) do you feel most comfortable with?\nWhat will be your biggest challenge in Writing Task 2?"
        ),

        // Phase 2: Days 7-13
        StudyDayEntity(
            id = 7,
            dayNumber = 7,
            phaseId = 2,
            weekNumber = 2,
            title = "Reading Strategies & Question Types",
            learningObjective = "Learn specific strategies for TFNG, Matching Headings, Multiple Choice, and Completion.",
            recommendedDurationMinutes = 120,
            examinerTip = "Different question types require different approaches. Matching headings requires identifying main ideas, while True/False/Not Given focuses on detailed understanding and inference.",
            commonMistake = "Applying the same reading strategy to all question types rather than adapting your approach.",
            reflectionQuestions = "Which reading question types seem most familiar to you?\nWhich ones seem most challenging?"
        ),
        StudyDayEntity(
            id = 8,
            dayNumber = 8,
            phaseId = 2,
            weekNumber = 2,
            title = "Keywords & Paraphrasing",
            learningObjective = "Master techniques for identifying keywords and building a 30-50 synonym list.",
            recommendedDurationMinutes = 120,
            examinerTip = "Many reading questions test your ability to recognize paraphrased information. Practice identifying ideas expressed in different words with the same meaning.",
            commonMistake = "Looking for exact word matches between question and text, rather than recognizing paraphrased ideas.",
            reflectionQuestions = "How comfortable are you with quickly identifying synonyms and paraphrases?\nWhat time management challenges do you anticipate in reading?"
        ),
        StudyDayEntity(
            id = 9,
            dayNumber = 9,
            phaseId = 2,
            weekNumber = 2,
            title = "Practice & Strategy Application",
            learningObjective = "Complete a full untimed reading practice test focusing on strategy over speed.",
            recommendedDurationMinutes = 180,
            examinerTip = "In your first practice test, focus on accuracy rather than speed. It's better to answer fewer questions correctly than rush through with many mistakes.",
            commonMistake = "Becoming discouraged by slow reading speed in initial tests when the focus should be technique.",
            reflectionQuestions = "Which question types did you find most challenging?\nHow effectively were you able to apply the strategies you've learned?"
        ),
        StudyDayEntity(
            id = 10,
            dayNumber = 10,
            phaseId = 2,
            weekNumber = 2,
            title = "In-Depth Error Analysis",
            learningObjective = "Analyze mistake patterns: word-grabbing, distractor traps, and assumption vs evidence.",
            recommendedDurationMinutes = 120,
            examinerTip = "Learning from your mistakes is the fastest way to improve. Many candidates repeat the same errors because they don't analyze what went wrong.",
            commonMistake = "Moving on to more practice without understanding the root causes of incorrect answers.",
            reflectionQuestions = "What patterns did you notice in your incorrect answers?\nWhich specific strategies need the most improvement?"
        ),
        StudyDayEntity(
            id = 11,
            dayNumber = 11,
            phaseId = 2,
            weekNumber = 2,
            title = "Second Full Reading Test",
            learningObjective = "Complete another full reading test applying lessons from error analysis.",
            recommendedDurationMinutes = 180,
            examinerTip = "For difficult passages, spend more time understanding the first paragraph, which contains the main idea and orients you to the topic.",
            commonMistake = "Spending equal time on all parts of a passage rather than prioritizing topic sentences and key paragraphs.",
            reflectionQuestions = "Did your accuracy improve compared to the previous test?\nWhich strategies worked well and which ones need adjustment?"
        ),
        StudyDayEntity(
            id = 12,
            dayNumber = 12,
            phaseId = 2,
            weekNumber = 2,
            title = "Timed Practice (Strict 60 Minutes)",
            learningObjective = "Simulate real exam conditions: exactly 60 minutes with 3 passages and transfer time.",
            recommendedDurationMinutes = 150,
            examinerTip = "Time management is crucial. If a question takes too long, mark it and come back later. You may find better context after reading other parts.",
            commonMistake = "Not practicing transferring answers to the answer sheet, which takes time and can lead to transcription errors under pressure.",
            reflectionQuestions = "How did time pressure affect your performance?\nWhat adjustments do you need to make to improve speed without sacrificing accuracy?"
        ),
        StudyDayEntity(
            id = 13,
            dayNumber = 13,
            phaseId = 2,
            weekNumber = 2,
            title = "Reading Self-Assessment & Review",
            learningObjective = "Calculate accuracy percentage across tests and complete Week 2 Self-Assessment.",
            recommendedDurationMinutes = 90,
            examinerTip = "Review your weakest question types and prepare an action plan before entering Writing phase.",
            commonMistake = "Skipping self-assessment review and carrying over unresolved reading doubts.",
            reflectionQuestions = "What are your top 2 strongest question types?\nWhat are your top 2 weakest question types requiring ongoing practice?"
        ),

        // Phase 3: Days 14-20
        StudyDayEntity(
            id = 14,
            dayNumber = 14,
            phaseId = 3,
            weekNumber = 3,
            title = "Planning, Question Analysis & Essay Structures",
            learningObjective = "Master the 6 IELTS essay structures and idea generation (5 Questions Method, Mind Mapping).",
            recommendedDurationMinutes = 150,
            examinerTip = "The first 5-10 minutes you spend analyzing the question and planning your essay are the most important part of the writing test. A clear plan leads to a coherent essay.",
            commonMistake = "Rushing to start writing without properly analyzing what the question is asking, leading to off-topic responses.",
            reflectionQuestions = "Which part of essay writing has been most challenging for you in the past?\nHow comfortable are you with identifying different Task 2 question types?"
        ),
        StudyDayEntity(
            id = 15,
            dayNumber = 15,
            phaseId = 3,
            weekNumber = 3,
            title = "Idea Generation & Introductions",
            learningObjective = "Practice writing strong introductions (paraphrase + thesis) for various essay types.",
            recommendedDurationMinutes = 150,
            examinerTip = "Having a bank of flexible examples you can adapt to different topics will save you valuable thinking time in the exam.",
            commonMistake = "Using memorized essays or template phrases that don't directly answer the specific prompt.",
            reflectionQuestions = "How easily can you generate ideas for unfamiliar topics?\nWhat strategies can you use to quickly plan relevant examples?"
        ),
        StudyDayEntity(
            id = 16,
            dayNumber = 16,
            phaseId = 3,
            weekNumber = 3,
            title = "Body Paragraphs & Cohesion",
            learningObjective = "Master paragraph structure: topic sentence, explanation, specific example, and linking.",
            recommendedDurationMinutes = 150,
            examinerTip = "Each body paragraph should discuss just ONE main idea. Multiple ideas in a single paragraph often lead to underdeveloped points and lower coherence scores.",
            commonMistake = "Writing body paragraphs that are too short or contain multiple underdeveloped ideas rather than one fully expanded point.",
            reflectionQuestions = "How do you currently structure your paragraphs?\nWhat techniques do you use to fully develop your main points?"
        ),
        StudyDayEntity(
            id = 17,
            dayNumber = 17,
            phaseId = 3,
            weekNumber = 3,
            title = "Conclusions & Topic Vocabulary",
            learningObjective = "Study conclusion strategies and build topic vocabulary for Education, Tech, Environment, Health.",
            recommendedDurationMinutes = 150,
            examinerTip = "You don't need fancy vocabulary to score well. It's better to use simpler words correctly than complex words incorrectly. Accuracy matters more than showing off uncommon words.",
            commonMistake = "Overusing complex vocabulary in an attempt to impress examiners, leading to inappropriate word choices and collocations.",
            reflectionQuestions = "What is your current approach to vocabulary in academic writing?\nHow confident are you in using a range of grammatical structures?"
        ),
        StudyDayEntity(
            id = 18,
            dayNumber = 18,
            phaseId = 3,
            weekNumber = 3,
            title = "Editing & Full Timed Practice (40 min)",
            learningObjective = "Write a complete Task 2 essay in 40 minutes and compare with Band 9 sample.",
            recommendedDurationMinutes = 180,
            examinerTip = "Always save 3-5 minutes at the end to review your essay for errors. Even small improvements in accuracy can lift your grammatical range and accuracy score.",
            commonMistake = "Not leaving time to check your work, resulting in avoidable errors that lower your score.",
            reflectionQuestions = "How does your essay compare with the Band 9 sample?\nWhat are the main differences you notice in structure, development, or language?"
        ),
        StudyDayEntity(
            id = 19,
            dayNumber = 19,
            phaseId = 3,
            weekNumber = 3,
            title = "Feedback & Common Errors Review",
            learningObjective = "Review personal error checklist for subject-verb agreement, articles, prepositions, tenses.",
            recommendedDurationMinutes = 120,
            examinerTip = "Professional feedback on just one or two essays can transform your writing by identifying patterns of errors you may not see yourself.",
            commonMistake = "Practicing quantity over quality - writing many essays without getting proper feedback or learning from mistakes.",
            reflectionQuestions = "What do you think are your main strengths in Task 2 writing?\nWhat specific aspects would you like expert feedback on?"
        ),
        StudyDayEntity(
            id = 20,
            dayNumber = 20,
            phaseId = 3,
            weekNumber = 3,
            title = "Writing Task 2 Assessment & Week 3 Review",
            learningObjective = "Write a second complete essay and complete Week 3 Self-Assessment checklist.",
            recommendedDurationMinutes = 120,
            examinerTip = "Ensure your essay hits 250+ words, addresses all prompt parts, and maintains a clear position throughout.",
            commonMistake = "Leaving an essay unfinished or writing under the 250-word minimum.",
            reflectionQuestions = "Did your second essay show improvement in structure and time management?\nWhich language skills need targeted attention?"
        ),

        // Phase 4: Days 21-27
        StudyDayEntity(
            id = 21,
            dayNumber = 21,
            phaseId = 4,
            weekNumber = 4,
            title = "Speaking Assessment Criteria & Formats",
            learningObjective = "Understand the 4 criteria: Fluency & Coherence, Lexical Resource, Grammar, Pronunciation.",
            recommendedDurationMinutes = 120,
            examinerTip = "Fluency doesn't mean speaking quickly - it means speaking without unnatural pauses. Speaking at a moderate pace with good connection between ideas will score higher than fast, disjointed speech.",
            commonMistake = "Memorizing scripted answers, which examiners are trained to recognize. This can lower your score significantly.",
            reflectionQuestions = "Which speaking criterion do you think will be most challenging for you?\nHow comfortable are you speaking about unfamiliar topics?"
        ),
        StudyDayEntity(
            id = 22,
            dayNumber = 22,
            phaseId = 4,
            weekNumber = 4,
            title = "Weakness Analysis & Part 1-3 Formats",
            learningObjective = "Self-assess current levels in each criterion and learn response strategies.",
            recommendedDurationMinutes = 120,
            examinerTip = "Many candidates focus only on grammar and vocabulary, but pronunciation is equally important. Work on your word stress, sentence stress, and intonation patterns.",
            commonMistake = "Giving very short answers in Part 1, which doesn't provide enough language for examiners to assess your abilities.",
            reflectionQuestions = "Which part of the speaking test seems most intimidating?\nWhat strategies can you use to extend your answers appropriately?"
        ),
        StudyDayEntity(
            id = 23,
            dayNumber = 23,
            phaseId = 4,
            weekNumber = 4,
            title = "Part 2 Strategy: The Long Turn (PREP)",
            learningObjective = "Master 1-minute preparation notes and speaking for exactly 2 minutes; learn 7 Part 3 question types.",
            recommendedDurationMinutes = 120,
            examinerTip = "In Part 2, the examiner won't interrupt you, so it's entirely up to you to manage your time. Practice speaking for the full 2 minutes - not shorter, not longer.",
            commonMistake = "Running out of things to say before the 2-minute mark or missing bullet points on the task card.",
            reflectionQuestions = "How comfortable are you speaking continuously for 2 minutes?\nWhat techniques could help you organize your thoughts during the 1-minute prep?"
        ),
        StudyDayEntity(
            id = 24,
            dayNumber = 24,
            phaseId = 4,
            weekNumber = 4,
            title = "Recording Practice & Critical Review",
            learningObjective = "Record Part 1 (10 questions) and Part 2 (3 topics); listen critically for filler words and pauses.",
            recommendedDurationMinutes = 150,
            examinerTip = "Record yourself speaking and then listen critically. Most candidates are surprised by patterns they didn't realize they had, like overusing 'um/like' or pronunciation slips.",
            commonMistake = "Practicing speaking only mentally without speaking out loud, which doesn't develop muscle memory.",
            reflectionQuestions = "What patterns did you notice when listening to your recording?\nWhich aspects of your speaking need the most attention?"
        ),
        StudyDayEntity(
            id = 25,
            dayNumber = 25,
            phaseId = 4,
            weekNumber = 4,
            title = "Mock Speaking Test with Partner/Feedback",
            learningObjective = "Simulate a complete speaking interview (Parts 1, 2, 3) and evaluate all 4 criteria.",
            recommendedDurationMinutes = 120,
            examinerTip = "Getting feedback from someone else is crucial because we often can't hear our own errors. Even practicing with a fluent peer provides valuable insights.",
            commonMistake = "Assuming general English conversation is identical to IELTS speaking. IELTS-specific structure is essential.",
            reflectionQuestions = "What specific feedback was most valuable to you?\nHow can you incorporate more speaking practice into your daily routine?"
        ),
        StudyDayEntity(
            id = 26,
            dayNumber = 26,
            phaseId = 4,
            weekNumber = 4,
            title = "Targeted Weakness Improvement",
            learningObjective = "Implement focused drills for fluency, topic vocabulary, complex structures, and intonation.",
            recommendedDurationMinutes = 120,
            examinerTip = "Focus on making improvements in one area at a time. Trying to fix everything at once leads to cognitive overload and can actually make performance worse.",
            commonMistake = "Not allowing enough time to build new speaking habits. Speaking skills improve gradually through consistent practice.",
            reflectionQuestions = "What is your biggest speaking improvement since the beginning of the week?\nWhat specific speaking habits do you need to continue working on?"
        ),
        StudyDayEntity(
            id = 27,
            dayNumber = 27,
            phaseId = 4,
            weekNumber = 4,
            title = "Speaking Assessment & Week 4 Review",
            learningObjective = "Record a full speaking test, compare with Day 24, and complete Week 4 Self-Assessment.",
            recommendedDurationMinutes = 120,
            examinerTip = "Notice how natural your pacing has become and note any remaining pronunciation challenges.",
            commonMistake = "Neglecting to document speaking milestones.",
            reflectionQuestions = "How did your fluency and confidence improve compared to Day 21?\nWhat are your top speaking priorities before test day?"
        ),

        // Phase 5: Days 28-34
        StudyDayEntity(
            id = 28,
            dayNumber = 28,
            phaseId = 5,
            weekNumber = 5,
            title = "Task 1 Essentials & Visual Types",
            learningObjective = "Study graphs, charts, tables, diagrams, maps; learn how to craft an overview paragraph.",
            recommendedDurationMinutes = 120,
            examinerTip = "For Academic Task 1, remember you're not asked to give your opinion or interpretations - just report key features objectively. For GT, understand the three letter types.",
            commonMistake = "Approaching all Task 1 questions with the same rigid structure rather than adapting to the visual type.",
            reflectionQuestions = "How familiar are you with the specific Task 1 requirements for your test type?\nWhat aspects of Task 1 seem most challenging?"
        ),
        StudyDayEntity(
            id = 29,
            dayNumber = 29,
            phaseId = 5,
            weekNumber = 5,
            title = "Structure, Trends & Comparisons",
            learningObjective = "Master 4-paragraph structure (Intro, Overview, Detail 1, Detail 2) and comparative language.",
            recommendedDurationMinutes = 120,
            examinerTip = "Don't try to describe every detail in the visual. Focus on selecting and reporting the most significant trends.",
            commonMistake = "Describing every minor data point rather than grouping and summarizing key trends.",
            reflectionQuestions = "How comfortable are you with describing data and identifying patterns?\nWhat time phrases and comparison markers will you use?"
        ),
        StudyDayEntity(
            id = 30,
            dayNumber = 30,
            phaseId = 5,
            weekNumber = 5,
            title = "Academic Vocabulary & Band 7+ Samples",
            learningObjective = "Study trend verbs/adverbs (soar, plummet, plateau, oscillate) and analyze sample responses.",
            recommendedDurationMinutes = 120,
            examinerTip = "Be precise with your language when describing data. Use accurate terms like 'fluctuated', 'peaked', or 'plateaued' rather than vague descriptions.",
            commonMistake = "Overusing the same descriptive terms (increased, decreased) rather than employing a range of precise vocabulary.",
            reflectionQuestions = "How varied is your vocabulary for describing trends and changes?\nWhat organizational structure will you use for your Task 1 responses?"
        ),
        StudyDayEntity(
            id = 31,
            dayNumber = 31,
            phaseId = 5,
            weekNumber = 5,
            title = "Full Practice (Untimed, 170-190 words)",
            learningObjective = "Analyze prompt, plan 4 paragraphs, write full response, and verify self-check criteria.",
            recommendedDurationMinutes = 150,
            examinerTip = "Spend at least 5 minutes analyzing the task and planning your response. A clear plan prevents an unbalanced or disorganized answer.",
            commonMistake = "Rushing into writing without proper analysis and planning, leading to missing key trends.",
            reflectionQuestions = "How easy or difficult was it to organize your Task 1 response?\nWhat aspects took the most time or caused uncertainty?"
        ),
        StudyDayEntity(
            id = 32,
            dayNumber = 32,
            phaseId = 5,
            weekNumber = 5,
            title = "Expert Feedback & Sample Comparison",
            learningObjective = "Compare your response to high-scoring model answers and identify areas for refinement.",
            recommendedDurationMinutes = 120,
            examinerTip = "Getting professional feedback on your writing is one of the most efficient ways to improve. A trained eye spots patterns you might miss.",
            commonMistake = "Continuing to practice without addressing fundamental issues in your overview or paragraph grouping.",
            reflectionQuestions = "What aspects of your Task 1 writing would benefit most from feedback?\nHow does your work compare to the sample answers you've studied?"
        ),
        StudyDayEntity(
            id = 33,
            dayNumber = 33,
            phaseId = 5,
            weekNumber = 5,
            title = "Weak Area Practice (20 min Timed)",
            learningObjective = "Practice on difficult visual types (maps, process diagrams, combined charts) timed to 20 minutes.",
            recommendedDurationMinutes = 120,
            examinerTip = "Task 1 is worth one-third of your writing score, so mastering it significantly impacts your overall result. Don't neglect it in favor of Task 2.",
            commonMistake = "Spending too little time practicing Task 1 because it seems simpler or is worth fewer marks than Task 2.",
            reflectionQuestions = "How has your approach to Task 1 evolved since the beginning of the week?\nWhat specific strategies have you developed for complex visuals?"
        ),
        StudyDayEntity(
            id = 34,
            dayNumber = 34,
            phaseId = 5,
            weekNumber = 5,
            title = "Task 1 Self-Assessment & Week 5 Review",
            learningObjective = "Complete a final timed 20-min task and Week 5 Self-Assessment checklist.",
            recommendedDurationMinutes = 120,
            examinerTip = "Ensure your overview contains NO specific numbers and your details support the overview directly.",
            commonMistake = "Including opinions or explanations of why data changed in Task 1.",
            reflectionQuestions = "Are you consistently writing 150+ words within 20 minutes?\nWhat is your final Task 1 confidence level out of 10?"
        ),

        // Phase 6: Days 35-41
        StudyDayEntity(
            id = 35,
            dayNumber = 35,
            phaseId = 6,
            weekNumber = 6,
            title = "Listening Fundamentals & Question Types",
            learningObjective = "Study all 4 listening sections, increasing difficulty, and 10+ question types.",
            recommendedDurationMinutes = 120,
            examinerTip = "The listening test progresses from easiest (Section 1) to hardest (Section 4). Many candidates lose easy marks in Section 1 because they're not fully concentrated from the beginning.",
            commonMistake = "Not reading questions before the audio plays, making it difficult to know what information to listen for.",
            reflectionQuestions = "What listening situations (conversations, monologues) are most challenging for you?\nWhich question types do you anticipate finding most difficult?"
        ),
        StudyDayEntity(
            id = 36,
            dayNumber = 36,
            phaseId = 6,
            weekNumber = 6,
            title = "Strategies, Prediction & Note-Taking",
            learningObjective = "Learn prediction techniques (word types, context) and note-taking abbreviations/symbols.",
            recommendedDurationMinutes = 120,
            examinerTip = "Prediction is crucial for listening success. Before each section plays, try to predict the type of information needed (e.g., name, number, date, or reason).",
            commonMistake = "Trying to understand every word rather than focusing on the specific information needed to answer the questions.",
            reflectionQuestions = "How effective are your current note-taking strategies?\nWhat prediction techniques work best for different question types?"
        ),
        StudyDayEntity(
            id = 37,
            dayNumber = 37,
            phaseId = 6,
            weekNumber = 6,
            title = "Full Listening Test 1",
            learningObjective = "Complete an authentic practice listening test using prediction and active note-taking.",
            recommendedDurationMinutes = 150,
            examinerTip = "When taking notes, don't try to write everything down. Focus on key information: names, numbers, dates, reasons, and opinions.",
            commonMistake = "Getting stuck on a missed question and losing focus on the next several answers. If you miss something, move on immediately.",
            reflectionQuestions = "Which sections or question types caused you the most difficulty?\nHow effective were your note-taking and prediction strategies?"
        ),
        StudyDayEntity(
            id = 38,
            dayNumber = 38,
            phaseId = 6,
            weekNumber = 6,
            title = "Listening Error Analysis & Common Traps",
            learningObjective = "Analyze wrong answers: distractors, similar sounds (15/50), singular/plural, spelling.",
            recommendedDurationMinutes = 120,
            examinerTip = "Many listening answers are missed not because you didn't hear them, but because you didn't recognize them due to paraphrasing.",
            commonMistake = "Listening for exact wording from the question, when IELTS audio uses synonyms and paraphrase.",
            reflectionQuestions = "Were errors due to not hearing or not understanding what you heard?\nWhich specific listening skills need the most improvement?"
        ),
        StudyDayEntity(
            id = 39,
            dayNumber = 39,
            phaseId = 6,
            weekNumber = 6,
            title = "Second Full Listening Test",
            learningObjective = "Apply refined strategies, strictly follow word limits, and eliminate spelling errors.",
            recommendedDurationMinutes = 150,
            examinerTip = "Always read instructions carefully. If it says 'NO MORE THAN TWO WORDS', writing three words gets zero marks regardless of content.",
            commonMistake = "Losing easy marks due to careless spelling mistakes in names, addresses, and numbers.",
            reflectionQuestions = "How has your performance improved compared to the first test?\nDid you strictly adhere to word count rules?"
        ),
        StudyDayEntity(
            id = 40,
            dayNumber = 40,
            phaseId = 6,
            weekNumber = 6,
            title = "Mock Day: Timed Conditions & 10-Min Transfer",
            learningObjective = "Simulate real exam without pausing; practice the crucial 10-minute answer transfer to sheet.",
            recommendedDurationMinutes = 180,
            examinerTip = "In the actual paper test, you'll have 10 minutes to transfer answers. Use this time not just for transfer but also to check spelling, plurals, and grammar.",
            commonMistake = "Forgetting to check spelling during transfer. Even if you heard the correct answer, spelling mistakes result in zero marks.",
            reflectionQuestions = "Did you complete all 40 questions and transfer them cleanly?\nAre there any blank answers left?"
        ),
        StudyDayEntity(
            id = 41,
            dayNumber = 41,
            phaseId = 6,
            weekNumber = 6,
            title = "Final Logistics, Checklist & Relaxation",
            learningObjective = "Verify documents (ID, confirmation), pack pencils, light review, mindset, and sleep.",
            recommendedDurationMinutes = 120,
            examinerTip = "Your performance on test day reflects your preparation over these six weeks. Trust in your practice and approach each section with calm confidence!",
            commonMistake = "Cramming late the night before test day and arriving fatigued at the test center.",
            reflectionQuestions = "Are your ID, confirmation, and pencils packed and ready?\nAre you ready to trust your 41 days of consistent training?"
        )
    )

    // Complete Audited List of Tasks & Subtasks
    // MANDATORY AUDIT RULE: ALL TASKS & SUBTASKS START UNCHECKED (NO PRE-COMPLETED STATES)
    val tasks = listOf(
        // Day 1
        StudyTaskEntity("d1_t1", 1, null, "Determine test type", "Confirm if you need Academic or General Training IELTS.", 20, "Logistics", displayOrder = 1),
        StudyTaskEntity("d1_t2", 1, null, "Set target band scores", "Set your overall target and per-section targets (Listening, Reading, Writing, Speaking).", 25, "Planning", displayOrder = 2),
        StudyTaskEntity("d1_t3", 1, null, "Review test format & scoring", "Understand timing, sections, and official band descriptors for each component.", 45, "Overview", displayOrder = 3),
        StudyTaskEntity("d1_t3_s1", 1, "d1_t3", "Listening format check", "4 sections, 40 questions, ~30 mins audio + 10 mins transfer time.", 10, "Overview", displayOrder = 4),
        StudyTaskEntity("d1_t3_s2", 1, "d1_t3", "Reading format check", "3 passages, 40 questions, strict 60 minutes with no transfer time.", 10, "Overview", displayOrder = 5),
        StudyTaskEntity("d1_t3_s3", 1, "d1_t3", "Writing format check", "Task 1 (20 mins, 150+ words); Task 2 (40 mins, 250+ words).", 10, "Overview", displayOrder = 6),
        StudyTaskEntity("d1_t3_s4", 1, "d1_t3", "Speaking format check", "3 parts face-to-face or video call, 11-14 minutes total.", 10, "Overview", displayOrder = 7),
        StudyTaskEntity("d1_t4", 1, null, "Confirm institutional requirements", "Contact your target institution/university to confirm minimum sectional scores.", 30, "Logistics", displayOrder = 8),

        // Day 2
        StudyTaskEntity("d2_t1", 2, null, "Complete Speaking Fundamentals lesson", "Review comprehensive lesson on IELTS Speaking test layout and parts.", 50, "Skill", displayOrder = 1),
        StudyTaskEntity("d2_t2", 2, null, "Take detailed notes on Speaking format", "Note the timing and differences between Part 1, Part 2 (long turn), and Part 3.", 30, "Strategy", displayOrder = 2),
        StudyTaskEntity("d2_t3", 2, null, "Study the 4 Speaking assessment criteria", "Master Fluency, Lexical Resource, Grammar, and Pronunciation.", 40, "Assessment", displayOrder = 3),
        StudyTaskEntity("d2_t3_s1", 2, "d2_t3", "Criterion 1: Fluency & Coherence", "Speaking at moderate pace without unnatural pauses, linking ideas.", 10, "Assessment", displayOrder = 4),
        StudyTaskEntity("d2_t3_s2", 2, "d2_t3", "Criterion 2: Lexical Resource", "Natural vocabulary, idioms, and topic collocations.", 10, "Assessment", displayOrder = 5),
        StudyTaskEntity("d2_t3_s3", 2, "d2_t3", "Criterion 3: Grammatical Range & Accuracy", "Complex structures, varied tenses, and accurate clauses.", 10, "Assessment", displayOrder = 6),
        StudyTaskEntity("d2_t3_s4", 2, "d2_t3", "Criterion 4: Pronunciation", "Word stress, sentence intonation, and rhythm.", 10, "Assessment", displayOrder = 7),

        // Day 3
        StudyTaskEntity("d3_t1", 3, null, "Complete Listening Fundamentals lesson", "Study audio delivery, question layouts, and 4 sections.", 40, "Skill", displayOrder = 1),
        StudyTaskEntity("d3_t2", 3, null, "Understand all listening question types", "Master the 6 key question types tested in the listening section.", 40, "Strategy", displayOrder = 2),
        StudyTaskEntity("d3_t2_s1", 3, "d3_t2", "Question Type: Multiple choice", "Predict answers and identify distractors.", 10, "Strategy", displayOrder = 3),
        StudyTaskEntity("d3_t2_s2", 3, "d3_t2", "Question Type: Matching", "Connect opinions or characteristics with options.", 10, "Strategy", displayOrder = 4),
        StudyTaskEntity("d3_t2_s3", 3, "d3_t2", "Question Type: Plan/map/diagram labeling", "Follow directional language and spatial cues.", 10, "Strategy", displayOrder = 5),
        StudyTaskEntity("d3_t2_s4", 3, "d3_t2", "Question Type: Form/note/table completion", "Predict word types and follow word count limits.", 10, "Strategy", displayOrder = 6),
        StudyTaskEntity("d3_t2_s5", 3, "d3_t2", "Question Type: Sentence completion", "Ensure grammatical fit and correct spelling.", 10, "Strategy", displayOrder = 7),
        StudyTaskEntity("d3_t2_s6", 3, "d3_t2", "Question Type: Short-answer questions", "Identify exact words required from audio.", 10, "Strategy", displayOrder = 8),
        StudyTaskEntity("d3_t3", 3, null, "Study prediction and section strategies", "Practice predicting grammatical forms and keywords before audio plays.", 25, "Strategy", displayOrder = 9),
        StudyTaskEntity("d3_t4", 3, null, "Note the scoring system", "Understand conversion from raw scores (0-40) to band scores.", 15, "Overview", displayOrder = 10),

        // Day 4
        StudyTaskEntity("d4_t1", 4, null, "Complete Reading Fundamentals lesson", "Study passage difficulty progression and scoring differences.", 40, "Skill", displayOrder = 1),
        StudyTaskEntity("d4_t2", 4, null, "Master skimming techniques", "Learn to read fast for general gist and main ideas without getting stuck.", 35, "Strategy", displayOrder = 2),
        StudyTaskEntity("d4_t3", 4, null, "Master scanning techniques", "Practice locating names, numbers, dates, and key synonyms rapidly.", 35, "Strategy", displayOrder = 3),
        StudyTaskEntity("d4_t4", 4, null, "Time management breakdown", "Study allocation: P1 (16m), P2 (17m), P3 (18m), Review/transfer (9m).", 10, "Strategy", displayOrder = 4),
        StudyTaskEntity("d4_t4_s1", 4, "d4_t4", "Passage 1 timing drill (16-17 min)", "Complete simpler passage with speed and high accuracy.", 10, "Strategy", displayOrder = 5),
        StudyTaskEntity("d4_t4_s2", 4, "d4_t4", "Passage 2 timing drill (17-18 min)", "Maintain steady scanning on moderate complexity passage.", 10, "Strategy", displayOrder = 6),
        StudyTaskEntity("d4_t4_s3", 4, "d4_t4", "Passage 3 timing drill (18-20 min)", "Tackle dense academic text and abstract questions.", 10, "Strategy", displayOrder = 7),
        StudyTaskEntity("d4_t4_s4", 4, "d4_t4", "Review buffer & answer transfer (5-7 min)", "Ensure all 40 answers are copied to sheet with zero blanks.", 10, "Strategy", displayOrder = 8),

        // Day 5
        StudyTaskEntity("d5_t1", 5, null, "Complete Writing Task 1 lesson", "Understand visual analysis for Academic and letter formatting for General Training.", 40, "Skill", displayOrder = 1),
        StudyTaskEntity("d5_t2", 5, null, "Understand Academic vs GT differences", "Analyze reporting visuals vs writing letters.", 25, "Strategy", displayOrder = 2),
        StudyTaskEntity("d5_t3", 5, null, "Academic Task 1 core techniques", "Master describing visuals, trends, and overviews.", 45, "Practice", displayOrder = 3),
        StudyTaskEntity("d5_t3_s1", 5, "d5_t3", "Study describing graphs, charts, tables", "Identify high/low points, trends, and outliers.", 15, "Practice", displayOrder = 4),
        StudyTaskEntity("d5_t3_s2", 5, "d5_t3", "Learn trend vocabulary", "Master verbs and adverbs for increasing, decreasing, fluctuating.", 15, "Vocabulary", displayOrder = 5),
        StudyTaskEntity("d5_t3_s3", 5, "d5_t3", "Practice overview statements", "Summarize overall trends without quoting specific numbers.", 15, "Practice", displayOrder = 6),

        // Day 6
        StudyTaskEntity("d6_t1", 6, null, "Complete Writing Task 2 Fundamentals lesson", "Study essay prompts, planning process, and marking criteria.", 45, "Skill", displayOrder = 1),
        StudyTaskEntity("d6_t2", 6, null, "Understand the 4 main essay types", "Study Opinion, Discussion, Problem-Solution, and Two-Part questions.", 40, "Strategy", displayOrder = 2),
        StudyTaskEntity("d6_t3", 6, null, "Study the 4 assessment criteria for writing", "Task Response, Coherence & Cohesion, Lexical Resource, Grammar.", 30, "Assessment", displayOrder = 3),
        StudyTaskEntity("d6_t4", 6, null, "Complete Week 1 Self-Assessment", "Review strengths, weaknesses, and format knowledge.", 35, "Assessment", displayOrder = 4),
        StudyTaskEntity("d6_t4_s1", 6, "d6_t4", "Identify top 3 current strengths", "Record in the Self-Assessment area.", 10, "Assessment", displayOrder = 5),
        StudyTaskEntity("d6_t4_s2", 6, "d6_t4", "Identify top 3 current weaknesses", "Note areas needing focused attention.", 10, "Assessment", displayOrder = 6),
        StudyTaskEntity("d6_t4_s3", 6, "d6_t4", "Set 2 priority improvement areas", "Formulate targeted preparation goals.", 10, "Assessment", displayOrder = 7),

        // Day 7
        StudyTaskEntity("d7_t1", 7, null, "Join Reading Challenge Lesson 1", "Begin structured reading mastery training.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d7_t2", 7, null, "Study different reading question types", "Learn specific approaches for each question type.", 35, "Strategy", displayOrder = 2),
        StudyTaskEntity("d7_t3", 7, null, "Question Type Strategies Checklist", "Master techniques for all 9 reading question types.", 50, "Strategy", displayOrder = 3),
        StudyTaskEntity("d7_t3_s1", 7, "d7_t3", "True/False/Not Given & Yes/No/Not Given", "Differentiate explicit contradiction from unmentioned facts.", 10, "Strategy", displayOrder = 4),
        StudyTaskEntity("d7_t3_s2", 7, "d7_t3", "Matching Headings", "Do first, summarize 1st and last sentences of paragraphs.", 10, "Strategy", displayOrder = 5),
        StudyTaskEntity("d7_t3_s3", 7, "d7_t3", "Multiple Choice", "Analyze difference in options and predict before reading.", 10, "Strategy", displayOrder = 6),
        StudyTaskEntity("d7_t3_s4", 7, "d7_t3", "Summary Completion", "Predict word types and verify grammatical consistency.", 10, "Strategy", displayOrder = 7),
        StudyTaskEntity("d7_t3_s5", 7, "d7_t3", "Matching Information & Sentence Endings", "Identify synonyms and eliminate impossible endings.", 10, "Strategy", displayOrder = 8),
        StudyTaskEntity("d7_t3_s6", 7, "d7_t3", "Labeling a Diagram & Short Answers", "Respect strict word limits and locate technical terms.", 10, "Strategy", displayOrder = 9),

        // Day 8
        StudyTaskEntity("d8_t1", 8, null, "Complete Reading Challenge Lesson 2", "Techniques for identifying keywords and question intent.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d8_t2", 8, null, "Master techniques for identifying keywords", "Underline nouns, dates, places, and core verbs.", 25, "Strategy", displayOrder = 2),
        StudyTaskEntity("d8_t2_s1", 8, "d8_t2", "Permanent keywords: Names, dates, locations, numbers", "Locate unchanging anchor points in both questions and passages.", 10, "Strategy", displayOrder = 3),
        StudyTaskEntity("d8_t2_s2", 8, "d8_t2", "Changeable keywords: Verbs, adjectives, abstract nouns", "Anticipate synonyms and paraphrases used in the passage.", 10, "Strategy", displayOrder = 4),
        StudyTaskEntity("d8_t3", 8, null, "Practice paraphrasing skills", "Recognize how questions rephrase passage ideas using synonyms.", 30, "Strategy", displayOrder = 5),
        StudyTaskEntity("d8_t3_s1", 8, "d8_t3", "Direct synonym substitution recognition", "Match synonyms with equivalent meanings (e.g. vital = crucial).", 10, "Strategy", displayOrder = 6),
        StudyTaskEntity("d8_t3_s2", 8, "d8_t3", "Word class transformations (verb to noun)", "Recognize shifts like 'to develop' -> 'the development of'.", 10, "Strategy", displayOrder = 7),
        StudyTaskEntity("d8_t3_s3", 8, "d8_t3", "Passive vs active voice transformation", "Follow agent and action across grammatical voice shifts.", 10, "Strategy", displayOrder = 8),
        StudyTaskEntity("d8_t4", 8, null, "Build synonym bank (30-50 words)", "Study and review synonym pairs in the vocabulary notebook.", 30, "Vocabulary", displayOrder = 9),

        // Day 9
        StudyTaskEntity("d9_t1", 9, null, "Complete Reading Challenge Lesson 3", "Final preparation before untimed diagnostic test.", 30, "Skill", displayOrder = 1),
        StudyTaskEntity("d9_t2", 9, null, "Complete full untimed Reading practice test", "Do all 3 passages focusing strictly on applying techniques without speed pressure.", 100, "Practice", displayOrder = 2),
        StudyTaskEntity("d9_t3", 9, null, "Focus on applying strategies, NOT speed", "Prioritize accuracy and deep comprehension.", 30, "Strategy", displayOrder = 3),
        StudyTaskEntity("d9_t4", 9, null, "Record raw scores in Practice Test Tracker", "Log scores for Passage 1, 2, 3 and note overall accuracy.", 20, "Review", displayOrder = 4),

        // Day 10
        StudyTaskEntity("d10_t1", 10, null, "Review practice test incorrect answers", "Examine every wrong answer to uncover why the mistake occurred.", 35, "Analysis", displayOrder = 1),
        StudyTaskEntity("d10_t2", 10, null, "Analyze why each wrong answer was incorrect", "Verify against passage text and grammatical context.", 30, "Analysis", displayOrder = 2),
        StudyTaskEntity("d10_t3", 10, null, "Identify patterns in mistakes", "Categorize mistakes using the 4-step error analysis strategy.", 30, "Analysis", displayOrder = 3),
        StudyTaskEntity("d10_t3_s1", 10, "d10_t3", "Check for word-grabbing without context", "Verify if word looked similar but context differed.", 10, "Analysis", displayOrder = 4),
        StudyTaskEntity("d10_t3_s2", 10, "d10_t3", "Check for missing paraphrasing & synonyms", "Locate text where synonyms expressed the question.", 10, "Analysis", displayOrder = 5),
        StudyTaskEntity("d10_t3_s3", 10, "d10_t3", "Check for confusing 'mentioned' with 'answer'", "Ensure statement directly answers prompt.", 10, "Analysis", displayOrder = 6),
        StudyTaskEntity("d10_t3_s4", 10, "d10_t3", "Check for distractor traps & assumptions", "Rely purely on textual evidence rather than logic.", 10, "Analysis", displayOrder = 7),
        StudyTaskEntity("d10_t4", 10, null, "Log errors in the Error Log", "Record question type, why wrong, and prevention strategy.", 25, "Review", displayOrder = 8),

        // Day 11
        StudyTaskEntity("d11_t1", 11, null, "Complete second full reading test", "Cambridge practice test focusing on finding correct textual evidence.", 100, "Practice", displayOrder = 1),
        StudyTaskEntity("d11_t2", 11, null, "Focus on finding correct answers rather than speed", "Apply steady scanning and question verification.", 30, "Strategy", displayOrder = 2),
        StudyTaskEntity("d11_t3", 11, null, "Apply lessons learned from Day 10 error analysis", "Verify that previous distractor traps and word-grabbing are avoided.", 30, "Strategy", displayOrder = 3),
        StudyTaskEntity("d11_t4", 11, null, "Log improvements in score", "Compare performance against Test 1 in the Practice Test Tracker.", 20, "Review", displayOrder = 4),

        // Day 12 & 13
        StudyTaskEntity("d12_t1", 12, null, "Timed reading practice (strict 60 min)", "Simulate exact test conditions: 3 passages in 60 minutes with transfer.", 60, "Practice", displayOrder = 1),
        StudyTaskEntity("d12_t2", 12, null, "Simulate real test conditions", "No breaks, no pausing, quiet environment.", 20, "Practice", displayOrder = 2),
        StudyTaskEntity("d12_t3", 12, null, "Assess reading speed (WPM) & pacing", "Check time per passage and transfer buffer.", 25, "Analysis", displayOrder = 3),
        StudyTaskEntity("d12_t4", 12, null, "Calculate accuracy percentage across 3 tests", "Compute total correct divided by 120 questions.", 20, "Analysis", displayOrder = 4),
        StudyTaskEntity("d12_t5", 12, null, "Identify 2-3 question types needing work", "Note challenging types for ongoing targeted practice.", 25, "Planning", displayOrder = 5),
        StudyTaskEntity("d13_t1", 13, null, "Complete Week 2 Self-Assessment", "Review reading speed, accuracy %, and list mastered vs weak question types.", 45, "Assessment", displayOrder = 1),
        StudyTaskEntity("d13_t1_s1", 13, "d13_t1", "Assess reading speed (target 200-250 WPM)", "Measure words per minute across academic articles.", 10, "Assessment", displayOrder = 2),
        StudyTaskEntity("d13_t1_s2", 13, "d13_t1", "Review accuracy across 3 full practice tests", "Calculate overall percentage and trends from diagnostic to timed.", 10, "Assessment", displayOrder = 3),
        StudyTaskEntity("d13_t1_s3", 13, "d13_t1", "Identify top 2 mastered question types", "Confirm high accuracy on strong question types.", 10, "Assessment", displayOrder = 4),
        StudyTaskEntity("d13_t1_s4", 13, "d13_t1", "Identify top 2 weakest question types needing drills", "Target specific question categories for ongoing practice.", 10, "Assessment", displayOrder = 5),
        StudyTaskEntity("d13_t2", 13, null, "Consolidate Reading Action Plan", "Set specific weekly drills to maintain reading skills.", 45, "Review", displayOrder = 6),
        StudyTaskEntity("d13_t2_s1", 13, "d13_t2", "Schedule daily 20-min reading comprehension drill", "Maintain reading pace and vocabulary review during writing phase.", 15, "Review", displayOrder = 7),

        // Day 14
        StudyTaskEntity("d14_t1", 14, null, "Join 5-Day Challenge (Complete Lesson 1)", "Learn prompt deconstruction and essay planning.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d14_t2", 14, null, "Complete Lesson 1 of Essay Builder Course", "Study fundamental structure of Task 2 essays.", 35, "Skill", displayOrder = 2),
        StudyTaskEntity("d14_t3", 14, null, "Focus on question analysis techniques", "Identify micro-keywords and instructions.", 30, "Strategy", displayOrder = 3),
        StudyTaskEntity("d14_t4", 14, null, "Master the 6 IELTS essay structures", "Study Opinion, Discussion, Problem-Solution, Advantages-Disadvantages, Two-Part, Direct.", 30, "Strategy", displayOrder = 4),
        StudyTaskEntity("d14_t5", 14, null, "Practice 5 Questions & Mind Mapping methods", "Brainstorm fast logical ideas for 2 unfamiliar essay topics.", 30, "Practice", displayOrder = 5),

        // Day 15
        StudyTaskEntity("d15_t1", 15, null, "Complete Lesson 2 of 5-Day Challenge", "Techniques for idea generation and planning.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d15_t2", 15, null, "Complete Lesson 2 of Essay Builder Course", "Learn how to develop strong introductions.", 35, "Skill", displayOrder = 2),
        StudyTaskEntity("d15_t3", 15, null, "Practice idea generation techniques", "Use brainstorming and micro-keyword branches.", 30, "Practice", displayOrder = 3),
        StudyTaskEntity("d15_t4", 15, null, "Write 4-5 different introduction paragraphs", "Write introductions for varied question types.", 50, "Practice", displayOrder = 4),
        StudyTaskEntity("d15_t4_s1", 15, "d15_t4", "Write Opinion Essay Introduction", "Paraphrase question + state clear position/thesis.", 15, "Practice", displayOrder = 5),
        StudyTaskEntity("d15_t4_s2", 15, "d15_t4", "Write Discussion Essay Introduction", "Paraphrase question + introduce both views + hint stance.", 15, "Practice", displayOrder = 6),
        StudyTaskEntity("d15_t4_s3", 15, "d15_t4", "Write Problem-Solution Introduction", "Paraphrase topic + state that problems and solutions will be analyzed.", 15, "Practice", displayOrder = 7),
        StudyTaskEntity("d15_t4_s4", 15, "d15_t4", "Write Two-Part Question Introduction", "Paraphrase both questions concisely.", 15, "Practice", displayOrder = 8),

        // Day 16
        StudyTaskEntity("d16_t1", 16, null, "Complete Lesson 3 of 5-Day Challenge", "Focus on body paragraph development.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d16_t2", 16, null, "Complete Lesson 3 of Essay Builder Course", "Master paragraph structure and cohesion.", 35, "Skill", displayOrder = 2),
        StudyTaskEntity("d16_t3", 16, null, "Practice writing well-developed body paragraphs", "Apply PEEL (Point, Explanation, Evidence, Link).", 40, "Practice", displayOrder = 3),
        StudyTaskEntity("d16_t4", 16, null, "Execute Body Paragraph Checklist", "Verify each paragraph adheres to coherence standards.", 40, "Review", displayOrder = 4),
        StudyTaskEntity("d16_t4_s1", 16, "d16_t4", "Paragraph 1: Clear topic sentence & 1 main idea", "Ensure single clear focus in the opening sentence.", 10, "Review", displayOrder = 5),
        StudyTaskEntity("d16_t4_s2", 16, "d16_t4", "Paragraph 1: Explanation (2-3 sent) & specific example", "Expand reason fully with realistic evidence.", 10, "Review", displayOrder = 6),
        StudyTaskEntity("d16_t4_s3", 16, "d16_t4", "Paragraph 2: Clear topic sentence & 1 main idea", "Transition to second main supporting argument.", 10, "Review", displayOrder = 7),
        StudyTaskEntity("d16_t4_s4", 16, "d16_t4", "Paragraph 2: Explanation & specific example", "Provide full reasoning and connection to question.", 10, "Review", displayOrder = 8),
        StudyTaskEntity("d16_t4_s5", 16, "d16_t4", "Check linking words and cohesion", "Verify smooth flow without mechanical overuse.", 10, "Review", displayOrder = 9),

        // Day 17
        StudyTaskEntity("d17_t1", 17, null, "Complete Lesson 4 of 5-Day Challenge", "Focus on conclusion strategies.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d17_t2", 17, null, "Complete Lesson 4 of Essay Builder Course", "Learn language enhancement and precision techniques.", 35, "Skill", displayOrder = 2),
        StudyTaskEntity("d17_t3", 17, null, "Study conclusion strategies", "Summarize main points in 2 sentences without introducing new points.", 25, "Strategy", displayOrder = 3),
        StudyTaskEntity("d17_t4", 17, null, "Build topic-specific vocabulary lists", "Create collocations and key terms across core IELTS themes.", 35, "Vocabulary", displayOrder = 4),
        StudyTaskEntity("d17_t4_s1", 17, "d17_t4", "Education Topic Vocabulary", "Curriculum, tertiary education, assessment, literacy, practical skills.", 10, "Vocabulary", displayOrder = 5),
        StudyTaskEntity("d17_t4_s2", 17, "d17_t4", "Technology Topic Vocabulary", "Automation, artificial intelligence, efficiency, digital tools.", 10, "Vocabulary", displayOrder = 6),
        StudyTaskEntity("d17_t4_s3", 17, "d17_t4", "Environment Topic Vocabulary", "Emissions, renewable energy, deforestation, biodiversity, pollution.", 10, "Vocabulary", displayOrder = 7),
        StudyTaskEntity("d17_t4_s4", 17, "d17_t4", "Health Topic Vocabulary", "Prevention, nutrition, diagnosis, chronic illness, treatment therapy.", 10, "Vocabulary", displayOrder = 8),
        StudyTaskEntity("d17_t5", 17, null, "Focus on accuracy over complexity", "Prioritize using natural common words accurately over misused uncommon words.", 20, "Strategy", displayOrder = 9),

        // Day 18
        StudyTaskEntity("d18_t1", 18, null, "Complete Lesson 5 of 5-Day Challenge", "Focus on editing and refining essays.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d18_t2", 18, null, "Complete Lesson 5 of Essay Builder Course", "Learn time management and final checks.", 35, "Skill", displayOrder = 2),
        StudyTaskEntity("d18_t3", 18, null, "Write a COMPLETE Task 2 essay (40 min timed)", "250+ words strictly timed to 40 minutes.", 40, "Practice", displayOrder = 3),
        StudyTaskEntity("d18_t4", 18, null, "Compare your essay with a Band 9 sample response", "Examine paragraph development and vocabulary range.", 35, "Review", displayOrder = 4),
        StudyTaskEntity("d18_t5", 18, null, "Practice editing and proofreading (save 3-5 min)", "Identify spelling slips, missing articles, and punctuation.", 15, "Review", displayOrder = 5),
        StudyTaskEntity("d18_t6", 18, null, "Record essay in Practice Test Tracker", "Log word count, time taken, and self-assessment scores.", 20, "Review", displayOrder = 6),

        // Day 19
        StudyTaskEntity("d19_t1", 19, null, "Review the complete essay writing process", "Synthesize planning, drafting, and editing steps.", 30, "Strategy", displayOrder = 1),
        StudyTaskEntity("d19_t2", 19, null, "Self-assess using official band descriptors", "Evaluate Task Response, Coherence, Lexical Resource, Grammar.", 30, "Assessment", displayOrder = 2),
        StudyTaskEntity("d19_t3", 19, null, "Identify recurring grammar/vocabulary errors", "Note personal slip patterns in your error log.", 30, "Analysis", displayOrder = 3),
        StudyTaskEntity("d19_t4", 19, null, "Review Common Errors Checklist", "Audit your writing against high-frequency errors.", 30, "Review", displayOrder = 4),
        StudyTaskEntity("d19_t4_s1", 19, "d19_t4", "Grammar: Subject-verb agreement & articles (a/an/the)", "Ensure singular/plural subjects agree with verbs; verify articles.", 10, "Review", displayOrder = 5),
        StudyTaskEntity("d19_t4_s2", 19, "d19_t4", "Grammar: Prepositions, tenses, fragments, run-ons", "Check preposition choices and sentence boundaries.", 10, "Review", displayOrder = 6),
        StudyTaskEntity("d19_t4_s3", 19, "d19_t4", "Vocabulary: Word choice, register, and spelling", "Eliminate informal slang, repeated words, and spelling errors.", 10, "Review", displayOrder = 7),
        StudyTaskEntity("d19_t4_s4", 19, "d19_t4", "Structure: Off-topic points, weak topic sentences, thesis", "Confirm every paragraph directly answers the prompt.", 10, "Review", displayOrder = 8),

        // Day 20
        StudyTaskEntity("d20_t1", 20, null, "Write a second complete Task 2 essay", "Apply all editing and paragraph techniques under 40 minutes.", 40, "Practice", displayOrder = 1),
        StudyTaskEntity("d20_t2", 20, null, "Check essay against official checklist", "Verify full compliance with band requirements.", 35, "Review", displayOrder = 2),
        StudyTaskEntity("d20_t2_s1", 20, "d20_t2", "Introduction: Paraphrased, clear thesis, 2-3 sent", "Confirm question is rephrased without copying words.", 10, "Review", displayOrder = 3),
        StudyTaskEntity("d20_t2_s2", 20, "d20_t2", "Body: 1 main idea each, topic sentences, examples", "Verify fully developed points and real-world evidence.", 10, "Review", displayOrder = 4),
        StudyTaskEntity("d20_t2_s3", 20, "d20_t2", "Conclusion: Summarizes points, no new info, 2 sent", "Restate overall position concisely.", 10, "Review", displayOrder = 5),
        StudyTaskEntity("d20_t2_s4", 20, "d20_t2", "Overall: 250+ words, all parts addressed, position maintained", "Ensure word count is comfortably above 250 words.", 10, "Review", displayOrder = 6),
        StudyTaskEntity("d20_t3", 20, null, "Complete Week 3 Self-Assessment", "Review essay structure check, language check, and action plan.", 45, "Assessment", displayOrder = 7),
        StudyTaskEntity("d20_t3_s1", 20, "d20_t3", "Essay structure check (4-5 paragraphs, clear thesis)", "Confirm introduction, 2 body paragraphs, and conclusion adhere to official standards.", 10, "Assessment", displayOrder = 8),
        StudyTaskEntity("d20_t3_s2", 20, "d20_t3", "Language & grammatical range self-evaluation", "Evaluate subject-verb agreement, complex sentence structures, and topic vocabulary.", 10, "Assessment", displayOrder = 9),
        StudyTaskEntity("d20_t3_s3", 20, "d20_t3", "Establish ongoing 40-minute essay writing schedule", "Set weekly essay practice schedule to maintain Task 2 momentum.", 10, "Assessment", displayOrder = 10),

        // Day 21
        StudyTaskEntity("d21_t1", 21, null, "Study the four speaking assessment criteria", "In-depth breakdown of Fluency, Lexical Resource, Grammar, Pronunciation.", 45, "Skill", displayOrder = 1),
        StudyTaskEntity("d21_t2", 21, null, "Understand format of Parts 1, 2, and 3", "Timing, examiner expectations, and question styles for all 3 parts.", 45, "Overview", displayOrder = 2),
        StudyTaskEntity("d21_t3", 21, null, "Note timing and expectations for each part", "Part 1 (4-5m familiar), Part 2 (3-4m cue card), Part 3 (4-5m abstract).", 30, "Strategy", displayOrder = 3),

        // Day 22
        StudyTaskEntity("d22_t1", 22, null, "Self-assess current level in each criterion", "Rate yourself on 1-9 scale for fluency, vocabulary, grammar, pronunciation.", 35, "Assessment", displayOrder = 1),
        StudyTaskEntity("d22_t2", 22, null, "Study common question types for all three parts", "Learn how to answer personal questions and abstract societal topics.", 35, "Strategy", displayOrder = 2),
        StudyTaskEntity("d22_t3", 22, null, "Learn response strategies for extending answers", "Use reasons, examples, and concessions to avoid short answers.", 25, "Strategy", displayOrder = 3),
        StudyTaskEntity("d22_t4", 22, null, "Identify top 3 speaking weaknesses", "Select specific targets (e.g. filler words, word stress, extending answers).", 25, "Planning", displayOrder = 4),

        // Day 23
        StudyTaskEntity("d23_t1", 23, null, "Study techniques for organizing thoughts quickly", "Structure 1-min preparation into Introduction, Details, Feelings.", 30, "Strategy", displayOrder = 1),
        StudyTaskEntity("d23_t2", 23, null, "Practice using 1-minute prep time effectively", "Write key bullet words, NOT full sentences.", 25, "Practice", displayOrder = 2),
        StudyTaskEntity("d23_t3", 23, null, "Practice speaking for EXACTLY 2 minutes", "Set a timer and avoid finishing early or trailing off.", 25, "Practice", displayOrder = 3),
        StudyTaskEntity("d23_t4", 23, null, "Learn the PREP method", "Point, Reason, Example, Point for crisp argument development.", 20, "Strategy", displayOrder = 4),
        StudyTaskEntity("d23_t5", 23, null, "Study Part 3 7 common question types", "Opinion, Evaluate, Future, Cause/Effect, Hypothetical, Compare/Contrast, Past.", 20, "Strategy", displayOrder = 5),
        StudyTaskEntity("d23_t6", 23, null, "Execute Practice Topics Checklist", "Record cue card responses for two authentic topics.", 30, "Practice", displayOrder = 6),
        StudyTaskEntity("d23_t6_s1", 23, "d23_t6", "Topic 1 (Person admired): Prepare 1-min notes", "Jot down who, why, examples, and impact.", 10, "Practice", displayOrder = 7),
        StudyTaskEntity("d23_t6_s2", 23, "d23_t6", "Topic 1 (Person admired): Record 2-minute talk", "Speak continuously for full 120 seconds.", 10, "Practice", displayOrder = 8),
        StudyTaskEntity("d23_t6_s3", 23, "d23_t6", "Topic 2 (Place visited): Prepare 1-min notes", "Jot down where, when, activities, and memories.", 10, "Practice", displayOrder = 9),
        StudyTaskEntity("d23_t6_s4", 23, "d23_t6", "Topic 2 (Place visited): Record 2-minute talk", "Speak continuously for full 120 seconds.", 10, "Practice", displayOrder = 10),

        // Day 24
        StudyTaskEntity("d24_t1", 24, null, "Record yourself answering 10 Part 1 questions", "Answer familiar topics (hobbies, hometown, studies, routine).", 40, "Practice", displayOrder = 1),
        StudyTaskEntity("d24_t2", 24, null, "Record yourself answering 3 Part 2 cue cards", "Simulate 1-min prep and 2-minute talk for each.", 45, "Practice", displayOrder = 2),
        StudyTaskEntity("d24_t3", 24, null, "Listen to ALL recordings critically", "Identify patterns: filler words (um/uh/like), pauses, pronunciation.", 45, "Analysis", displayOrder = 3),
        StudyTaskEntity("d24_t4", 24, null, "Document recording analysis in Journal", "Record fluency issues, vocabulary repetitions, and intonation.", 20, "Review", displayOrder = 4),

        // Day 25
        StudyTaskEntity("d25_t1", 25, null, "Find practice partner or mock test service", "Arrange authentic speaking test partner or tutor.", 30, "Logistics", displayOrder = 1),
        StudyTaskEntity("d25_t2", 25, null, "Complete full mock speaking test (all 3 parts)", "Conduct uninterrupted 11-14 minute interview simulation.", 45, "Practice", displayOrder = 2),
        StudyTaskEntity("d25_t3", 25, null, "Get feedback on all four assessment criteria", "Score FC, LR, GRA, and Pronunciation.", 25, "Assessment", displayOrder = 3),
        StudyTaskEntity("d25_t4", 25, null, "Record mock session in Practice Test Tracker", "Log estimated band, strengths, and priority improvements.", 20, "Review", displayOrder = 4),

        // Day 26
        StudyTaskEntity("d26_t1", 26, null, "Create focused improvement plan for challenges", "Select weak criteria and design targeted exercises.", 30, "Planning", displayOrder = 1),
        StudyTaskEntity("d26_t2", 26, null, "Execute Improvement Focus Areas", "Drill specific weak speaking components.", 50, "Practice", displayOrder = 2),
        StudyTaskEntity("d26_t2_s1", 26, "d26_t2", "Fluency drills: practice with timer & reduce fillers", "Eliminate 'um/like' by using intentional silent pauses.", 15, "Practice", displayOrder = 3),
        StudyTaskEntity("d26_t2_s2", 26, "d26_t2", "Vocabulary drills: topic collocations & paraphrasing", "Practice using advanced collocations in natural sentences.", 15, "Vocabulary", displayOrder = 4),
        StudyTaskEntity("d26_t2_s3", 26, "d26_t2", "Grammar drills: complex sentences & varied tenses", "Practice conditionals, relative clauses, and perfect tenses.", 15, "Practice", displayOrder = 5),
        StudyTaskEntity("d26_t2_s4", 26, "d26_t2", "Pronunciation drills: word stress & sentence rhythm", "Shadow native speech audio to adopt authentic rhythm.", 15, "Practice", displayOrder = 6),
        StudyTaskEntity("d26_t3", 26, null, "Record yourself again to check improvement", "Compare re-recording against baseline.", 40, "Review", displayOrder = 7),

        // Day 27
        StudyTaskEntity("d27_t1", 27, null, "Record full speaking test (Part 1 + 2 + 3)", "Simulate complete test under authentic exam timing.", 45, "Practice", displayOrder = 1),
        StudyTaskEntity("d27_t2", 27, null, "Self-assess using official band descriptors", "Rate each criterion out of 9.", 30, "Assessment", displayOrder = 2),
        StudyTaskEntity("d27_t3", 27, null, "Compare with Day 24 recordings", "Calculate band progress and note remaining gaps.", 25, "Review", displayOrder = 3),
        StudyTaskEntity("d27_t4", 27, null, "Complete Week 4 Self-Assessment", "Review speaking progress check, part-specific check, and daily practice plan.", 40, "Assessment", displayOrder = 4),
        StudyTaskEntity("d27_t4_s1", 27, "d27_t4", "Part 1 comfort and answer extension self-evaluation", "Confirm ability to expand familiar answers without hesitation.", 10, "Assessment", displayOrder = 5),
        StudyTaskEntity("d27_t4_s2", 27, "d27_t4", "Part 2 timing (sustained 2 minutes without trailing off)", "Verify notes usage and full 2-minute talk coverage.", 10, "Assessment", displayOrder = 6),
        StudyTaskEntity("d27_t4_s3", 27, "d27_t4", "Part 3 abstract reasoning & PREP method application", "Evaluate structuring complex arguments with reasons and examples.", 10, "Assessment", displayOrder = 7),
        StudyTaskEntity("d27_t4_s4", 27, "d27_t4", "Set daily 15-minute speaking maintenance routine", "Formulate daily shadow speaking and recording drill schedule.", 10, "Assessment", displayOrder = 8),

        // Day 28
        StudyTaskEntity("d28_t1", 28, null, "Study Academic Task 1 Guide (Visuals)", "Understand reporting visual data objectively.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d28_t2", 28, null, "Learn to describe graphs, charts, tables, processes, maps", "Identify key conventions for each graphic type.", 30, "Overview", displayOrder = 2),
        StudyTaskEntity("d28_t3", 28, null, "Understand how to write an overview", "The most important paragraph! 2-3 sentences summarizing main trends with NO numbers.", 30, "Strategy", displayOrder = 3),
        StudyTaskEntity("d28_t4", 28, null, "Study data selection (key features only)", "Learn to group details rather than describing every single figure.", 25, "Strategy", displayOrder = 4),
        StudyTaskEntity("d28_t5", 28, null, "Review Task 1 Visual Types Checklist", "Familiarize yourself with the 7 possible graphic formats.", 20, "Strategy", displayOrder = 5),
        StudyTaskEntity("d28_t5_s1", 28, "d28_t5", "Line graph: trends over time and intersections", "Focus on overall trajectory and significant spikes.", 10, "Strategy", displayOrder = 6),
        StudyTaskEntity("d28_t5_s2", 28, "d28_t5", "Bar chart: category comparisons and rankings", "Highlight highest and lowest categories.", 10, "Strategy", displayOrder = 7),
        StudyTaskEntity("d28_t5_s3", 28, "d28_t5", "Pie chart: proportions and percentage shares", "Identify dominant slices and smallest segments.", 10, "Strategy", displayOrder = 8),
        StudyTaskEntity("d28_t5_s4", 28, "d28_t5", "Table: complex numerical data grouping", "Group columns/rows logically by patterns.", 10, "Strategy", displayOrder = 9),
        StudyTaskEntity("d28_t5_s5", 28, "d28_t5", "Process diagram: chronological steps and passive voice", "Describe natural or manufacturing cycles sequentially.", 10, "Strategy", displayOrder = 10),
        StudyTaskEntity("d28_t5_s6", 28, "d28_t5", "Map: spatial changes and development over time", "Use directional prepositions (north, south, adjacent).", 10, "Strategy", displayOrder = 11),
        StudyTaskEntity("d28_t5_s7", 28, "d28_t5", "Combined charts: relationships between two visuals", "Synthesize findings from both visual sources.", 10, "Strategy", displayOrder = 12),

        // Day 29
        StudyTaskEntity("d29_t1", 29, null, "Master 4-paragraph response structure", "Intro (1-2 sent), Overview (2-3 sent), Detail 1 (3-4 sent), Detail 2 (3-4 sent).", 35, "Strategy", displayOrder = 1),
        StudyTaskEntity("d29_t2", 29, null, "Learn to identify trends", "Distinguish increase, decrease, fluctuate, stable, and plateau.", 30, "Vocabulary", displayOrder = 2),
        StudyTaskEntity("d29_t3", 29, null, "Practice making comparisons", "Use comparative structures: higher than, lower than, similar to, whereas, while.", 30, "Practice", displayOrder = 3),
        StudyTaskEntity("d29_t4", 29, null, "Study overview writing rules", "Ensure overview has NO specific numbers; reports 2-3 main trends.", 25, "Strategy", displayOrder = 4),

        // Day 30
        StudyTaskEntity("d30_t1", 30, null, "Study sample answers (minimum 3 Band 7+)", "Analyze structure, overview quality, and vocabulary precision.", 35, "Skill", displayOrder = 1),
        StudyTaskEntity("d30_t2", 30, null, "Analyze structure and vocabulary used", "Note precise verbs, adverbs, and comparative prepositions.", 30, "Analysis", displayOrder = 2),
        StudyTaskEntity("d30_t3", 30, null, "Refine vocabulary lists", "Review soar, plummet, level off, fluctuate mildly, peak at.", 30, "Vocabulary", displayOrder = 3),
        StudyTaskEntity("d30_t4", 30, null, "Practice using new vocabulary in sentences", "Draft 5 sentences describing complex trend data.", 25, "Practice", displayOrder = 4),

        // Day 31
        StudyTaskEntity("d31_t1", 31, null, "Spend 5 minutes analyzing task and planning", "Highlight key trends, highest/lowest points, and anomalies.", 25, "Planning", displayOrder = 1),
        StudyTaskEntity("d31_t2", 31, null, "Plan response (structure, key points)", "Draft overview and organize 2 detail paragraph groupings.", 25, "Planning", displayOrder = 2),
        StudyTaskEntity("d31_t3", 31, null, "Write complete Task 1 response (170-190 words)", "Focus on quality, objective reporting, and clarity.", 60, "Practice", displayOrder = 3),
        StudyTaskEntity("d31_t4", 31, null, "Execute Task 1 Self-Check Checklist", "Verify compliance with the 9 essential criteria.", 40, "Review", displayOrder = 4),
        StudyTaskEntity("d31_t4_s1", 31, "d31_t4", "Introduction paraphrases the task", "Do not copy question word-for-word.", 10, "Review", displayOrder = 5),
        StudyTaskEntity("d31_t4_s2", 31, "d31_t4", "Overview included with main trends (NO data)", "Verify absence of any numbers in the overview.", 10, "Review", displayOrder = 6),
        StudyTaskEntity("d31_t4_s3", 31, "d31_t4", "Specific data in body paragraphs with comparisons", "Back up overview with selected figures.", 10, "Review", displayOrder = 7),
        StudyTaskEntity("d31_t4_s4", 31, "d31_t4", "Appropriate vocabulary & 150+ words confirmed", "Check word count and trend descriptors.", 10, "Review", displayOrder = 8),
        StudyTaskEntity("d31_t4_s5", 31, "d31_t4", "Accurate reporting & NO opinion given", "Strictly factual and objective reporting.", 10, "Review", displayOrder = 9),

        // Day 32
        StudyTaskEntity("d32_t1", 32, null, "Compare Day 31 response with model answers", "Benchmark overview clarity and detail grouping against Band 8/9 models.", 40, "Analysis", displayOrder = 1),
        StudyTaskEntity("d32_t2", 32, null, "Seek professional feedback or self-evaluate", "Evaluate Task Achievement, Coherence, Lexical Resource, Grammar.", 35, "Assessment", displayOrder = 2),
        StudyTaskEntity("d32_t3", 32, null, "Identify specific areas for improvement", "Check vocabulary variety, grammatical accuracy, and data selection.", 25, "Analysis", displayOrder = 3),
        StudyTaskEntity("d32_t4", 32, null, "Log feedback summary in Study Journal", "Document key learnings to avoid bad writing habits.", 20, "Review", displayOrder = 4),

        // Day 33
        StudyTaskEntity("d33_t1", 33, null, "Practice on difficult visual types", "Focus on process diagrams, maps, or complex comparative tables.", 45, "Practice", displayOrder = 1),
        StudyTaskEntity("d33_t2", 33, null, "Complete 2-3 Task 1 responses on weak areas", "Apply targeted grouping and vocabulary.", 45, "Practice", displayOrder = 2),
        StudyTaskEntity("d33_t3", 33, null, "Time yourself strictly (20 minutes per task)", "Simulate official 20-minute exam conditions.", 30, "Practice", displayOrder = 3),

        // Day 34
        StudyTaskEntity("d34_t1", 34, null, "Write final timed Task 1 response (20 min)", "Deliver complete 150+ word response from prompt analysis to proofreading.", 35, "Practice", displayOrder = 1),
        StudyTaskEntity("d34_t2", 34, null, "Verify Academic Checklist Compliance", "Complete comprehensive self-assessment on all Task 1 standards.", 35, "Review", displayOrder = 2),
        StudyTaskEntity("d34_t2_s1", 34, "d34_t2", "Overview clearly states main trends (NO data)", "Verify clear summary statement.", 10, "Review", displayOrder = 3),
        StudyTaskEntity("d34_t2_s2", 34, "d34_t2", "Data selection is appropriate (key features only)", "Ensure no minor clutter.", 10, "Review", displayOrder = 4),
        StudyTaskEntity("d34_t2_s3", 34, "d34_t2", "Accurate data reporting & comparisons made", "Confirm numbers match graph exactly.", 10, "Review", displayOrder = 5),
        StudyTaskEntity("d34_t2_s4", 34, "d34_t2", "Good vocabulary range & NO opinion given", "Ensure variety of trend words.", 10, "Review", displayOrder = 6),
        StudyTaskEntity("d34_t2_s5", 34, "d34_t2", "Clear structure & ideal length (150-180 words)", "Verify paragraph breaks and pacing.", 10, "Review", displayOrder = 7),
        StudyTaskEntity("d34_t3", 34, null, "Complete Week 5 Self-Assessment", "Evaluate data identification, language, organization, and action plan.", 45, "Assessment", displayOrder = 8),
        StudyTaskEntity("d34_t3_s1", 34, "d34_t3", "Confirm completing Task 1 within 20 minutes", "Verify speed from question prompt to final editing.", 10, "Assessment", displayOrder = 9),
        StudyTaskEntity("d34_t3_s2", 34, "d34_t3", "Overview writing verified across all visual types", "Confirm ability to craft 2-sentence overview with NO numbers.", 10, "Assessment", displayOrder = 10),
        StudyTaskEntity("d34_t3_s3", 34, "d34_t3", "Data grouping and comparison precision checked", "Verify logical categorization in Detail 1 and Detail 2 paragraphs.", 10, "Assessment", displayOrder = 11),

        // Day 35
        StudyTaskEntity("d35_t1", 35, null, "Study complete listening test format", "Understand sections 1 to 4, increasing difficulty, and scoring scale.", 40, "Skill", displayOrder = 1),
        StudyTaskEntity("d35_t2", 35, null, "Understand all question types (10+ types)", "Form completion, notes, table, multiple choice, matching, maps, sentence.", 40, "Strategy", displayOrder = 2),
        StudyTaskEntity("d35_t3", 35, null, "Learn section-specific characteristics", "Section 1 (social 2 speakers), Section 2 (monologue), Section 3 (academic 4 speakers), Section 4 (monologue lecture).", 25, "Strategy", displayOrder = 3),
        StudyTaskEntity("d35_t4", 35, null, "Study Section 1 concentration strategy", "Ensure zero careless errors on names, addresses, numbers in Section 1.", 15, "Strategy", displayOrder = 4),

        // Day 36
        StudyTaskEntity("d36_t1", 36, null, "Study prediction techniques in detail", "Predict word types (noun, adjective, number, date) before audio starts.", 35, "Strategy", displayOrder = 1),
        StudyTaskEntity("d36_t2", 36, null, "Learn note-taking shorthand & symbols", "Practice abbreviations (govt, approx, max) and symbols (&, w/, ->, ↑, ↓).", 30, "Skill", displayOrder = 2),
        StudyTaskEntity("d36_t3", 36, null, "Practice reading questions before audio plays", "Underline key question words in silent audio breaks.", 25, "Practice", displayOrder = 3),
        StudyTaskEntity("d36_t4", 36, null, "Apply Prediction Strategies Checklist", "Follow systematic steps before and during each audio section.", 30, "Strategy", displayOrder = 4),
        StudyTaskEntity("d36_t4_s1", 36, "d36_t4", "Before audio: Read instructions, underline keywords", "Identify word limit restrictions (e.g. NO MORE THAN TWO WORDS).", 10, "Strategy", displayOrder = 5),
        StudyTaskEntity("d36_t4_s2", 36, "d36_t4", "Before audio: Predict needed word type", "Anticipate noun, verb, number, or adjective.", 10, "Strategy", displayOrder = 6),
        StudyTaskEntity("d36_t4_s3", 36, "d36_t4", "During listening: Watch for distractors and corrections", "Listen for speakers changing their mind mid-sentence.", 10, "Strategy", displayOrder = 7),
        StudyTaskEntity("d36_t4_s4", 36, "d36_t4", "Note spelling carefully and do not panic if missed", "If an answer is missed, immediately move to the next question.", 10, "Strategy", displayOrder = 8),

        // Day 37
        StudyTaskEntity("d37_t1", 37, null, "Complete full authentic Listening practice test", "Cambridge practice test with sections 1 to 4 under exam conditions.", 60, "Practice", displayOrder = 1),
        StudyTaskEntity("d37_t2", 37, null, "Practice reading questions before audio plays", "Maintain disciplined question previewing throughout.", 25, "Strategy", displayOrder = 2),
        StudyTaskEntity("d37_t3", 37, null, "Use prediction techniques & shorthand notes", "Take quick notes during fast speaker exchanges.", 25, "Strategy", displayOrder = 3),
        StudyTaskEntity("d37_t4", 37, null, "Score each section and record in Tracker", "Calculate total score out of 40 and log predicted band.", 40, "Review", displayOrder = 4),

        // Day 38
        StudyTaskEntity("d38_t1", 38, null, "Review ALL incorrect answers from Day 37", "Identify whether mistakes were caused by distractors, spelling, or mishearing.", 40, "Analysis", displayOrder = 1),
        StudyTaskEntity("d38_t2", 38, null, "Understand error patterns (distractor, accents)", "Check for numbers misheard (15 vs 50) and plural 's'.", 30, "Analysis", displayOrder = 2),
        StudyTaskEntity("d38_t3", 38, null, "Identify your weakest section", "Determine whether Section 2 maps or Section 3 multiple choice caused losses.", 20, "Analysis", displayOrder = 3),
        StudyTaskEntity("d38_t4", 38, null, "Review common listening traps", "Information corrected mid-sentence, similar sounds, singular vs plural.", 20, "Strategy", displayOrder = 4),
        StudyTaskEntity("d38_t5", 38, null, "Log mistake patterns in Error Log", "Document mistake category, distractor trigger, and avoidance plan.", 20, "Review", displayOrder = 5),

        // Day 39
        StudyTaskEntity("d39_t1", 39, null, "Complete second full Listening practice test", "Cambridge authentic test applying distractor vigilance.", 60, "Practice", displayOrder = 1),
        StudyTaskEntity("d39_t2", 39, null, "Apply refined strategies from Day 38", "Stay vigilant against self-correction traps and multiple choice tricks.", 30, "Strategy", displayOrder = 2),
        StudyTaskEntity("d39_t3", 39, null, "STRICTLY follow word count instructions", "Ensure NO MORE THAN TWO WORDS / ONE NUMBER rules are followed.", 30, "Strategy", displayOrder = 3),
        StudyTaskEntity("d39_t4", 39, null, "Practice accurate spelling", "Check words commonly misspelled (calendar, accommodation, government).", 30, "Review", displayOrder = 4),

        // Day 40
        StudyTaskEntity("d40_t1", 40, null, "Complete full Listening test WITHOUT pausing", "Exact exam audio simulation with no pauses or rewinds.", 45, "Practice", displayOrder = 1),
        StudyTaskEntity("d40_t2", 40, null, "Simulate real exam conditions exactly", "No interruptions, headphones/speakers set to standard volume.", 25, "Practice", displayOrder = 2),
        StudyTaskEntity("d40_t3", 40, null, "Practice 10-minute answer transfer to sheet", "Transfer all 40 answers to official-style answer sheet within 10 minutes.", 30, "Strategy", displayOrder = 3),
        StudyTaskEntity("d40_t4", 40, null, "Complete Answer Transfer Checklist", "Verify error-free transfer of all 40 answers.", 40, "Review", displayOrder = 4),
        StudyTaskEntity("d40_t4_s1", 40, "d40_t4", "Transferred all 40 answers within 10 minutes", "Check that answer numbers match question numbers.", 10, "Review", displayOrder = 5),
        StudyTaskEntity("d40_t4_s2", 40, "d40_t4", "Double-checked spelling (especially names, places)", "Verify every letter is clear and legible.", 10, "Review", displayOrder = 6),
        StudyTaskEntity("d40_t4_s3", 40, "d40_t4", "Verified word count limits (no extra words)", "Ensure compliance with word constraints.", 10, "Review", displayOrder = 7),
        StudyTaskEntity("d40_t4_s4", 40, "d40_t4", "Checked for plural/singular accuracy & capitalization", "Confirm nouns match singular/plural audio evidence.", 10, "Review", displayOrder = 8),
        StudyTaskEntity("d40_t4_s5", 40, "d40_t4", "No answers left blank", "Make educated guesses for any missed questions.", 10, "Review", displayOrder = 9),

        // Day 41
        StudyTaskEntity("d41_t1", 41, null, "Complete Test Day Preparation Checklist", "Pack documents and required exam materials.", 35, "Logistics", displayOrder = 1),
        StudyTaskEntity("d41_t1_s1", 41, "d41_t1", "Documents to bring: Valid ID (passport/national ID) & confirmation", "Ensure name matches registration exactly.", 10, "Logistics", displayOrder = 2),
        StudyTaskEntity("d41_t1_s2", 41, "d41_t1", "What to bring: 2-3 sharpened pencils, eraser, sharpener, clear water bottle", "Prepare permitted stationary items.", 10, "Logistics", displayOrder = 3),
        StudyTaskEntity("d41_t1_s3", 41, "d41_t1", "What NOT to bring: phone, smartwatch, notes, study materials, food", "Leave prohibited electronics safely at home.", 10, "Logistics", displayOrder = 4),
        StudyTaskEntity("d41_t2", 41, null, "Verify test center location & arrival time", "Confirm travel route; plan to arrive at least 30 minutes early.", 20, "Logistics", displayOrder = 5),
        StudyTaskEntity("d41_t3", 41, null, "Conduct light strategy review", "Brief review of key reminders for Listening, Reading, Writing, Speaking.", 25, "Review", displayOrder = 6),
        StudyTaskEntity("d41_t4", 41, null, "Apply Test Day Mindset Checklist", "Follow relaxing preparation routine for maximum alertness.", 25, "Logistics", displayOrder = 7),
        StudyTaskEntity("d41_t4_s1", 41, "d41_t4", "The night before: No heavy study, pack items, 2-3 alarms, 8 hrs sleep", "Avoid caffeine after 6 PM and relax.", 10, "Logistics", displayOrder = 8),
        StudyTaskEntity("d41_t4_s2", 41, "d41_t4", "Test morning: Good breakfast, arrive 30 mins early, use bathroom", "Stay calm and confident.", 10, "Logistics", displayOrder = 9),
        StudyTaskEntity("d41_t4_s3", 41, "d41_t4", "During test: Manage time, move on if stuck, read carefully, review", "Trust in your 41 days of preparation.", 10, "Logistics", displayOrder = 10),
        StudyTaskEntity("d41_t5", 41, null, "Complete Week 6 Self-Assessment & Final Readiness", "Review listening accuracy, question-type comfort, and mental preparation.", 45, "Assessment", displayOrder = 11),
        StudyTaskEntity("d41_t5_s1", 41, "d41_t5", "Test day logistics & transport route planned", "Confirm arrival time with 30-min buffer and required documents.", 10, "Logistics", displayOrder = 12),
        StudyTaskEntity("d41_t5_s2", 41, "d41_t5", "Mindset calm, confidence, and sleep preparation verified", "Commit to trusting preparation and resting before exam day.", 10, "Overview", displayOrder = 13),
        StudyTaskEntity("d41_t5_s3", 41, "d41_t5", "Affirm completion of 6-week preparation curriculum", "Review all 4 skills mastery milestones across 41 days.", 10, "Assessment", displayOrder = 14)
    )

    // Curated 35+ Synonyms from Markdown Day 8
    val vocabularyList = listOf(
        VocabularyEntryEntity(originalWord = "Important", synonyms = "significant, vital, crucial, essential", meaning = "Of great significance or value", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Increase", synonyms = "rise, grow, expand, climb", meaning = "Become or make greater in size or amount", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Decrease", synonyms = "decline, reduce, drop, diminish", meaning = "Become smaller or fewer in size or quantity", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Show", synonyms = "illustrate, demonstrate, present, indicate, reveal", meaning = "Allow or cause to be visible", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Problem", synonyms = "issue, difficulty, challenge, obstacle", meaning = "A matter or situation regarded as unwelcome", topicTag = "Task 2", isReference = true),
        VocabularyEntryEntity(originalWord = "Solution", synonyms = "answer, remedy, resolution, fix", meaning = "A means of solving a problem or difficult situation", topicTag = "Task 2", isReference = true),
        VocabularyEntryEntity(originalWord = "Change", synonyms = "alter, modify, transform, shift", meaning = "Make or become different", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Result", synonyms = "outcome, consequence, effect", meaning = "A thing that is caused or produced by something else", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Cause", synonyms = "reason, factor, source", meaning = "A person or thing that gives rise to an action or phenomenon", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Help", synonyms = "assist, support, aid", meaning = "Make it easier for someone to do something", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Research", synonyms = "study, investigation, analysis", meaning = "Systematic investigation into materials and sources", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Method", synonyms = "technique, approach, procedure", meaning = "A particular procedure for accomplishing or approaching something", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Opinion", synonyms = "view, belief, idea, perspective", meaning = "A view or judgment formed about something", topicTag = "Task 2", isReference = true),
        VocabularyEntryEntity(originalWord = "Advantage", synonyms = "benefit, strength, positive feature", meaning = "A condition or circumstance that puts one in a favorable position", topicTag = "Task 2", isReference = true),
        VocabularyEntryEntity(originalWord = "Disadvantage", synonyms = "drawback, weakness, limitation", meaning = "An unfavorable circumstance or condition", topicTag = "Task 2", isReference = true),
        VocabularyEntryEntity(originalWord = "Similar", synonyms = "alike, comparable, relative", meaning = "Resembling without being identical", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Different", synonyms = "distinct, separate, varied", meaning = "Not the same as another or each other", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Rapid", synonyms = "fast, quick, speedy", meaning = "Happening in a short time or at a great rate", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Slow", synonyms = "gradual, steady, delayed", meaning = "Moving or operating at a low speed", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Large", synonyms = "huge, massive, vast", meaning = "Of considerable or relatively great size or extent", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Small", synonyms = "tiny, minimal, slight, modest", meaning = "Of a size that is less than normal", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Increase sharply", synonyms = "soar, spike, escalate", meaning = "Rise dramatically in a short period", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Decrease sharply", synonyms = "plummet, crash, collapse", meaning = "Fall precipitously and rapidly", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Improve", synonyms = "enhance, boost, strengthen", meaning = "Make or become better", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Worsen", synonyms = "decline, deteriorate, weaken", meaning = "Make or become worse", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Understand", synonyms = "comprehend, grasp, perceive", meaning = "Perceive the intended meaning of words or ideas", topicTag = "Reading", isReference = true),
        VocabularyEntryEntity(originalWord = "Build", synonyms = "construct, create, develop", meaning = "Construct by putting parts or materials together", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Destroy", synonyms = "ruin, damage, demolish", meaning = "End the existence of by damaging or attacking", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Reduce", synonyms = "cut down, lessen, lower", meaning = "Make smaller or less in amount, degree, or size", topicTag = "Task 1", isReference = true),
        VocabularyEntryEntity(originalWord = "Replace", synonyms = "substitute, exchange, swap", meaning = "Take the place of something", topicTag = "General", isReference = true),
        VocabularyEntryEntity(originalWord = "Predict", synonyms = "forecast, anticipate, expect", meaning = "Estimate that a specified thing will happen in the future", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Require", synonyms = "demand, need, call for", meaning = "Need for a particular purpose", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Pollution", synonyms = "contamination, toxicity", meaning = "Introduction of harmful materials into the environment", topicTag = "Environment", isReference = true),
        VocabularyEntryEntity(originalWord = "Climate change", synonyms = "global warming, environmental shift", meaning = "Long-term shifts in temperatures and weather patterns", topicTag = "Environment", isReference = true),
        VocabularyEntryEntity(originalWord = "Emissions", synonyms = "gases, pollutants, carbon output", meaning = "Production and discharge of something, especially gas or radiation", topicTag = "Environment", isReference = true),
        VocabularyEntryEntity(originalWord = "Renewable energy", synonyms = "sustainable energy, green energy", meaning = "Energy from a source that is not depleted when used", topicTag = "Environment", isReference = true),
        VocabularyEntryEntity(originalWord = "Technology", synonyms = "innovation, digital tools", meaning = "Application of scientific knowledge for practical purposes", topicTag = "Technology", isReference = true),
        VocabularyEntryEntity(originalWord = "Artificial intelligence", synonyms = "machine intelligence, AI systems", meaning = "Simulation of human intelligence processes by machines", topicTag = "Technology", isReference = true),
        VocabularyEntryEntity(originalWord = "Efficiency", synonyms = "productivity, effectiveness", meaning = "State or quality of being efficient", topicTag = "Academic", isReference = true),
        VocabularyEntryEntity(originalWord = "Curriculum", synonyms = "syllabus, academic program", meaning = "Subjects comprising a course of study in a school or college", topicTag = "Education", isReference = true),
        VocabularyEntryEntity(originalWord = "Assessment", synonyms = "evaluation, test, examination", meaning = "Evaluation or estimation of the nature, quality, or ability", topicTag = "Education", isReference = true),
        VocabularyEntryEntity(originalWord = "Literacy", synonyms = "reading ability, writing ability", meaning = "The ability to read and write", topicTag = "Education", isReference = true)
    )

    // Historical reference practice tests (labeled as historical, does NOT count as user progress)
    val referencePracticeTests = listOf(
        PracticeTestEntity(
            testDate = "2024-12-12",
            section = "Reading",
            sourceBook = "Official Cambridge IELTS",
            testNumber = "Test 1 (Diagnostic Untimed)",
            correctAnswers = 24,
            totalQuestions = 40,
            estimatedBand = 6.0,
            timeTakenMinutes = 75,
            isTimed = false,
            notes = "Historical reference test from preparation notes. P1: 9/13, P2: 8/13, P3: 7/14. Untimed practice focused on understanding strategies.",
            isHistoricalReference = true
        ),
        PracticeTestEntity(
            testDate = "2026-01-15",
            section = "Reading",
            sourceBook = "Cambridge IELTS 17",
            testNumber = "Test 2 (Untimed)",
            correctAnswers = 30,
            totalQuestions = 40,
            estimatedBand = 7.0,
            timeTakenMinutes = 70,
            isTimed = false,
            notes = "Historical reference test from notes. P1: 11/13, P2: 9/13, P3: 10/14. +6 questions improvement from Test 1 after error analysis.",
            isHistoricalReference = true
        ),
        PracticeTestEntity(
            testDate = "2026-01-16",
            section = "Reading",
            sourceBook = "Official Cambridge IELTS",
            testNumber = "Test 3 (Timed 60 min)",
            correctAnswers = 14,
            totalQuestions = 40,
            estimatedBand = 4.5,
            timeTakenMinutes = 60,
            isTimed = true,
            notes = "Historical reference test showing impact of strict time pressure. Rushed section, 0 mins left for review. Highlights need for time management.",
            isHistoricalReference = true
        )
    )

    // Historical reference error logs from Markdown Day 11 (labeled clearly as historical examples)
    val referenceErrorLogs = listOf(
        ErrorLogEntity(
            date = "2026-01-15",
            section = "Reading",
            testReference = "Cambridge 17 Test 2 - Dead Sea Scrolls",
            questionNumber = "Q2",
            questionType = "Notes Completion",
            originalAnswer = "Scrolls",
            correctAnswer = "Cave",
            reasonForError = "Word-grabbing without context (saw 'scrolls' nearby in passage). Ignored the verb 'went into,' which grammatically requires a location/place.",
            mistakeCategory = "Word-Grabbing Without Context",
            strategyToAvoid = "Always check the grammatical structure of the blank before writing. Confirm if the word logically fits the sentence.",
            reviewStatus = 2,
            isHistoricalReference = true
        ),
        ErrorLogEntity(
            date = "2026-01-15",
            section = "Reading",
            testReference = "Cambridge 17 Test 2 - Dead Sea Scrolls",
            questionNumber = "Q4",
            questionType = "Notes Completion",
            originalAnswer = "Judea",
            correctAnswer = "Essenes",
            reasonForError = "Confused 'mentioned' with 'answer'. Judea was a geographical location mentioned in the text, not the group of people.",
            mistakeCategory = "Confusing Mentioned with Answer",
            strategyToAvoid = "For 'who/which group' questions, identify the subject group (people nouns), not nearby place names.",
            reviewStatus = 2,
            isHistoricalReference = true
        ),
        ErrorLogEntity(
            date = "2026-01-15",
            section = "Reading",
            testReference = "Cambridge 17 Test 2 - Domesticating the Tomato",
            questionNumber = "Q14",
            questionType = "Multiple Choice – Purpose",
            originalAnswer = "A",
            correctAnswer = "C",
            reasonForError = "Misunderstood question type (purpose vs detail). Focused only on the first sentence instead of the entire paragraph.",
            mistakeCategory = "Not Understanding Question Types",
            strategyToAvoid = "Read the entire paragraph before answering 'purpose' questions. Ask: 'What is the function of this paragraph?' not 'What detail is mentioned?'",
            reviewStatus = 2,
            isHistoricalReference = true
        ),
        ErrorLogEntity(
            date = "2026-01-15",
            section = "Reading",
            testReference = "Cambridge 17 Test 2 - Domesticating the Tomato",
            questionNumber = "Q19",
            questionType = "Researcher Matching",
            originalAnswer = "C",
            correctAnswer = "B",
            reasonForError = "Used logic instead of textual evidence. Distractor trap: another researcher was mentioned in the vicinity but did not state the idea.",
            mistakeCategory = "Distractor Trap",
            strategyToAvoid = "Match exact textual evidence, not assumptions. Highlight researcher names and link them directly to quotes or attribution markers.",
            reviewStatus = 2,
            isHistoricalReference = true
        ),
        ErrorLogEntity(
            date = "2026-01-15",
            section = "Reading",
            testReference = "Cambridge 17 Test 2 - Insight or Evolution?",
            questionNumber = "Q32",
            questionType = "True/False/Not Given",
            originalAnswer = "YES",
            correctAnswer = "NO",
            reasonForError = "Used personal logic instead of textual evidence. Didn't verify the exact contradiction in the passage.",
            mistakeCategory = "Using Assumptions Instead of Evidence",
            strategyToAvoid = "Always verify with strict textual evidence. Remember: 'NO/FALSE' means the passage explicitly contradicts the statement.",
            reviewStatus = 2,
            isHistoricalReference = true
        ),
        ErrorLogEntity(
            date = "2026-01-15",
            section = "Reading",
            testReference = "Cambridge 17 Test 2 - Insight or Evolution?",
            questionNumber = "Q40",
            questionType = "Matching Information",
            originalAnswer = "G",
            correctAnswer = "B",
            reasonForError = "Poor time management at the end of the test. Distractor trap caused by rushing.",
            mistakeCategory = "Time Management Problems",
            strategyToAvoid = "Allocate time evenly across all 3 passages (approx 17-18 mins each). Slow down for the final questions: accuracy is better than rushing.",
            reviewStatus = 1,
            isHistoricalReference = true
        )
    )

    // Initial Weekly Assessments for Weeks 1 to 6 (all start uncompleted)
    val weeklyAssessments = (1..6).map { weekNum ->
        WeeklyAssessmentEntity(
            weekNumber = weekNum,
            answersJson = "{}",
            reflections = "",
            actionPlan = "",
            isCompleted = false,
            completedAt = null
        )
    }
}
