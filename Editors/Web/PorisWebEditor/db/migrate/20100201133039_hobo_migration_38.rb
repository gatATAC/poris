class HoboMigration38 < ActiveRecord::Migration
  def self.up
    add_index :task_assignments, [:task_id]
    add_index :task_assignments, [:project_membership_id]
    add_index :task_assignments, [:status_id]

    add_index :stories, [:project_id]
    add_index :stories, [:status_id]
    add_index :stories, [:milestone_id]

    add_index :phases, [:dev_plan_id]

    add_index :scope_kinds, [:type]

    add_index :task_statuses, [:status_id]

    add_index :projects, [:owner_id]

    add_index :project_memberships, [:project_id]
    add_index :project_memberships, [:user_id]
    add_index :project_memberships, [:role_id]

    add_index :tasks, [:story_id]

    add_index :milestones, [:project_id]
    add_index :milestones, [:phase_id]

    add_index :users, [:state]

    add_index :labels, [:node_id]
    add_index :labels, [:scope_kind_id]

    add_index :bugs, [:owner_id]
    add_index :bugs, [:modulo_id]

    add_index :nodes, [:type]
    add_index :nodes, [:default_value_id]
    add_index :nodes, [:default_mode_id]
    add_index :nodes, [:project_id]
    add_index :nodes, [:node_type_id]
    add_index :nodes, [:parent_id]
    add_index :nodes, [:root_id]
  end

  def self.down
    remove_index :task_assignments, :name => :index_task_assignments_on_task_id rescue ActiveRecord::StatementInvalid
    remove_index :task_assignments, :name => :index_task_assignments_on_project_membership_id rescue ActiveRecord::StatementInvalid
    remove_index :task_assignments, :name => :index_task_assignments_on_status_id rescue ActiveRecord::StatementInvalid

    remove_index :stories, :name => :index_stories_on_project_id rescue ActiveRecord::StatementInvalid
    remove_index :stories, :name => :index_stories_on_status_id rescue ActiveRecord::StatementInvalid
    remove_index :stories, :name => :index_stories_on_milestone_id rescue ActiveRecord::StatementInvalid

    remove_index :phases, :name => :index_phases_on_dev_plan_id rescue ActiveRecord::StatementInvalid

    remove_index :scope_kinds, :name => :index_scope_kinds_on_type rescue ActiveRecord::StatementInvalid

    remove_index :task_statuses, :name => :index_task_statuses_on_status_id rescue ActiveRecord::StatementInvalid

    remove_index :projects, :name => :index_projects_on_owner_id rescue ActiveRecord::StatementInvalid

    remove_index :project_memberships, :name => :index_project_memberships_on_project_id rescue ActiveRecord::StatementInvalid
    remove_index :project_memberships, :name => :index_project_memberships_on_user_id rescue ActiveRecord::StatementInvalid
    remove_index :project_memberships, :name => :index_project_memberships_on_role_id rescue ActiveRecord::StatementInvalid

    remove_index :tasks, :name => :index_tasks_on_story_id rescue ActiveRecord::StatementInvalid

    remove_index :milestones, :name => :index_milestones_on_project_id rescue ActiveRecord::StatementInvalid
    remove_index :milestones, :name => :index_milestones_on_phase_id rescue ActiveRecord::StatementInvalid

    remove_index :users, :name => :index_users_on_state rescue ActiveRecord::StatementInvalid

    remove_index :labels, :name => :index_labels_on_node_id rescue ActiveRecord::StatementInvalid
    remove_index :labels, :name => :index_labels_on_scope_kind_id rescue ActiveRecord::StatementInvalid

    remove_index :bugs, :name => :index_bugs_on_owner_id rescue ActiveRecord::StatementInvalid
    remove_index :bugs, :name => :index_bugs_on_modulo_id rescue ActiveRecord::StatementInvalid

    remove_index :nodes, :name => :index_nodes_on_type rescue ActiveRecord::StatementInvalid
    remove_index :nodes, :name => :index_nodes_on_default_value_id rescue ActiveRecord::StatementInvalid
    remove_index :nodes, :name => :index_nodes_on_default_mode_id rescue ActiveRecord::StatementInvalid
    remove_index :nodes, :name => :index_nodes_on_project_id rescue ActiveRecord::StatementInvalid
    remove_index :nodes, :name => :index_nodes_on_node_type_id rescue ActiveRecord::StatementInvalid
    remove_index :nodes, :name => :index_nodes_on_parent_id rescue ActiveRecord::StatementInvalid
    remove_index :nodes, :name => :index_nodes_on_root_id rescue ActiveRecord::StatementInvalid
  end
end
