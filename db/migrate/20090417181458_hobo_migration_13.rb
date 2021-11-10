class HoboMigration13 < ActiveRecord::Migration
  def self.up
    create_table :task_assignment_statuses do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
    end
    
    create_table :task_statuses do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :status_id
    end
    
    add_column :task_assignments, :status_id, :integer
  end

  def self.down
    remove_column :task_assignments, :status_id
    
    drop_table :task_assignment_statuses
    drop_table :task_statuses
  end
end
