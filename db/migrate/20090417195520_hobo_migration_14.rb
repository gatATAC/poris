class HoboMigration14 < ActiveRecord::Migration
  def self.up
    add_column :task_assignments, :project_membership_id, :integer
    remove_column :task_assignments, :user_id
  end

  def self.down
    remove_column :task_assignments, :project_membership_id
    add_column :task_assignments, :user_id, :integer
  end
end
