class HoboMigration53 < ActiveRecord::Migration
  def self.up
    drop_table :story_statuses
    drop_table :stories
    drop_table :milestones
    drop_table :tasks
    #drop_table :task_assignment_statuses
    #drop_table :task_statuses
    drop_table :bugs
    drop_table :modulos
    drop_table :task_assignments
    drop_table :dev_plans
    drop_table :phases
  end

  def self.down
    create_table "story_statuses", :force => true do |t|
      t.string   "name"
      t.datetime "created_at"
      t.datetime "updated_at"
    end

    create_table "stories", :force => true do |t|
      t.string   "title"
      t.text     "body"
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "project_id"
      t.integer  "status_id"
      t.integer  "milestone_id"
    end

    add_index "stories", ["milestone_id"], :name => "index_stories_on_milestone_id"
    add_index "stories", ["project_id"], :name => "index_stories_on_project_id"
    add_index "stories", ["status_id"], :name => "index_stories_on_status_id"

    create_table "milestones", :force => true do |t|
      t.string   "name"
      t.date     "deadline"
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "project_id"
      t.text     "description"
      t.integer  "position"
      t.integer  "phase_id"
    end

    add_index "milestones", ["phase_id"], :name => "index_milestones_on_phase_id"
    add_index "milestones", ["project_id"], :name => "index_milestones_on_project_id"

    create_table "tasks", :force => true do |t|
      t.text     "description"
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "story_id"
      t.integer  "position"
      t.string   "name"
    end

    add_index "tasks", ["story_id"], :name => "index_tasks_on_story_id"

    create_table "task_assignment_statuses", :force => true do |t|
      t.string   "name"
      t.datetime "created_at"
      t.datetime "updated_at"
    end

    create_table "task_statuses", :force => true do |t|
      t.string   "name"
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "status_id"
    end

    add_index "task_statuses", ["status_id"], :name => "index_task_statuses_on_status_id"

    create_table "bugs", :force => true do |t|
      t.string   "name"
      t.text     "description"
      t.boolean  "solved"
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "owner_id"
      t.integer  "modulo_id"
    end

    add_index "bugs", ["modulo_id"], :name => "index_bugs_on_modulo_id"
    add_index "bugs", ["owner_id"], :name => "index_bugs_on_owner_id"

    create_table "modulos", :force => true do |t|
      t.string   "name"
      t.datetime "created_at"
      t.datetime "updated_at"
    end

    create_table "task_assignments", :force => true do |t|
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "task_id"
      t.integer  "status_id"
      t.integer  "project_membership_id"
    end

    add_index "task_assignments", ["project_membership_id"], :name => "index_task_assignments_on_project_membership_id"
    add_index "task_assignments", ["status_id"], :name => "index_task_assignments_on_status_id"
    add_index "task_assignments", ["task_id"], :name => "index_task_assignments_on_task_id"

    create_table "dev_plans", :force => true do |t|
      t.string   "name"
      t.string   "abbrev"
      t.text     "description"
      t.datetime "created_at"
      t.datetime "updated_at"
    end

    create_table "phases", :force => true do |t|
      t.string   "name"
      t.string   "abbrev"
      t.text     "description"
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "dev_plan_id"
      t.integer  "position"
    end

    add_index "phases", ["dev_plan_id"], :name => "index_phases_on_dev_plan_id"
  end
end
