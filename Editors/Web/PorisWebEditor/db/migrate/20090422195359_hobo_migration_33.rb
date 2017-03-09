class HoboMigration33 < ActiveRecord::Migration
  def self.up
    create_table :dev_plans do |t|
      t.string   :name
      t.string   :abbrev
      t.text     :description
      t.datetime :created_at
      t.datetime :updated_at
    end
    
    create_table :phases do |t|
      t.string   :name
      t.string   :abbrev
      t.text     :description
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :dev_plan_id
      t.integer  :position
    end
    
    add_column :milestones, :description, :text
    add_column :milestones, :position, :integer
    add_column :milestones, :phase_id, :integer
  end

  def self.down
    remove_column :milestones, :description
    remove_column :milestones, :position
    remove_column :milestones, :phase_id
    
    drop_table :dev_plans
    drop_table :phases
  end
end
