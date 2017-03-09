class HoboMigration12 < ActiveRecord::Migration
  def self.up
    add_column :task_assignments, :role_id, :integer
  end

  def self.down
    remove_column :task_assignments, :role_id
  end
end
