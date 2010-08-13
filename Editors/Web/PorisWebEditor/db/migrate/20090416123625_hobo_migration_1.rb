class HoboMigration1 < ActiveRecord::Migration
  def self.up
    create_table :nodes do |t|
      t.string   :name
      t.float    :version
      t.integer  :variant_id
      t.integer  :type
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :owner_id
      t.integer  :node_type_id
    end
    
    create_table :node_memberships do |t|
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :node_id
      t.integer  :user_id
    end
    
    create_table :projects do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
    end
    
    create_table :stories do |t|
      t.string   :title
      t.text     :body
      t.string   :status
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :project_id
    end
    
    create_table :tasks do |t|
      t.string   :description
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :story_id
    end
    
    create_table :task_assignments do |t|
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :user_id
      t.integer  :task_id
    end
    
    create_table :teams do |t|
      t.string   :name
      t.integer  :owner_id
      t.datetime :created_at
      t.datetime :updated_at
    end
    
    create_table :team_memberships do |t|
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :team_id
      t.integer  :user_id
    end
    
    create_table :team_node_memberships do |t|
      t.datetime :created_at
      t.datetime :updated_at
    end
    
    create_table :users do |t|
      t.string   :crypted_password, :limit => 40
      t.string   :salt, :limit => 40
      t.string   :remember_token
      t.datetime :remember_token_expires_at
      t.string   :name
      t.string   :email_address
      t.boolean  :administrator, :default => false
      t.datetime :created_at
      t.datetime :updated_at
      t.string   :state, :default => "active"
      t.datetime :key_timestamp
    end
    
    create_table :variants do |t|
      t.string   :name
      t.integer  :node_id
      t.integer  :variant_node_id
      t.datetime :created_at
      t.datetime :updated_at
    end
  end

  def self.down
    drop_table :nodes
    drop_table :node_memberships
    drop_table :projects
    drop_table :stories
    drop_table :tasks
    drop_table :task_assignments
    drop_table :teams
    drop_table :team_memberships
    drop_table :team_node_memberships
    drop_table :users
    drop_table :variants
  end
end
