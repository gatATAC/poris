class HoboMigration10 < ActiveRecord::Migration
  def self.up
    create_table :milestones do |t|
      t.string   :name
      t.date     :deadline
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :project_id
    end
    
    add_column :stories, :milestone_id, :integer
  end

  def self.down
    remove_column :stories, :milestone_id
    
    drop_table :milestones
  end
end
